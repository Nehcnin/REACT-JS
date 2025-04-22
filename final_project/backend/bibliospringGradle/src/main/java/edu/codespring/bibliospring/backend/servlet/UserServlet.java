package edu.codespring.bibliospring.backend.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.jdbc.ConnectionManager;
import edu.codespring.bibliospring.backend.repository.jdbc.JdbcRepositoryFactory;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.ServiceFactory;
import edu.codespring.bibliospring.backend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@WebServlet("/login")
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS fejlécek hozzáadása minden válaszhoz
        setCorsHeaders(resp);

        JSONObject json = servletJsonInit(req, resp);
        String login = json.getString("login");
        String username = json.getString("username");
        String password = json.getString("password");
        LOG.debug("Credentials received");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        boolean ok;

        UserService userService = ServiceFactory.getUserService();

        if (login.equals("true")) {
            try {
                ok = userService.login(user);
            } catch (ServiceException e) {
                LOG.error("Error while logging in user " + username);
                throw new ServletException(e);
            }
        } else {
            try {
                user = userService.register(user);
            } catch (Exception e) {
                LOG.error("Error while registering user " + username);
                throw new ServletException(e);
            }
            ok = user != null;
        }

        UserRepository userRepository = JdbcRepositoryFactory.getInstance().getUserRepository();
        User authenticatedUser = userRepository.getByUsername(username);
        if (authenticatedUser != null && ok) {
            if (login.equals("true")) {
                LOG.debug("User " + username + " logged in successfully");
            } else {
                LOG.debug("User " + username + " registered successfully");
            }
            LOG.debug("sending response");
            resp.getWriter().write("{\"status\": \"success\"}");
        } else {
            if (login.equals("true")) {
                LOG.error("Error while logging in user " + username);
            } else {
                LOG.error("Error while registering user " + username);
            }
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"status\": \"error\"}");
        }
    }

    // Az OPTIONS kérések kezeléséhez
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);  // 200 OK válasz az OPTIONS kérésre
    }

    // CORS fejlécek beállítása
    private static void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    static JSONObject servletJsonInit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Biztosítjuk a CORS fejléceket a válaszban
        setCorsHeaders(resp);
        resp.setContentType("application/json");

        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        return new JSONObject(jsonBuffer.toString());
    }
}
