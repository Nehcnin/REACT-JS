package edu.codespring.bibliospring.backend.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.jdbc.JdbcRepositoryFactory;
import edu.codespring.bibliospring.backend.service.ServiceFactory;
import edu.codespring.bibliospring.backend.service.UserService;
import edu.codespring.bibliospring.backend.utils.PasswordEncrypter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static edu.codespring.bibliospring.backend.servlet.UserServlet.servletJsonInit;

@WebServlet("/passwordChange")
public class PasswordChangeServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordChangeServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS fejlécek beállítása
        setCorsHeaders(resp);

        JSONObject json = servletJsonInit(req, resp);
        String username = json.getString("username");
        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");

        User user = new User();
        user.setUsername(username);
        user.setPassword(oldPassword);
        boolean ok;

        UserService userService = ServiceFactory.getUserService();
        ok = userService.login(user);

        UserRepository userRepository = JdbcRepositoryFactory.getInstance().getUserRepository();

        User authenticatedUser = userRepository.getByUsername(username);
        if (authenticatedUser != null && ok) {
            authenticatedUser.setPassword(PasswordEncrypter.generateHashPassword(newPassword, authenticatedUser.getUuid()));
            userRepository.update(authenticatedUser);
            LOG.info("User " + authenticatedUser.getUsername() + " changed password successfully");
            resp.getWriter().write("{\"status\": \"success\"}");
        } else {
            LOG.error("Error while changing password for " + authenticatedUser.getUsername());
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"status\": \"error\"}");
        }
    }

    // CORS fejlécek beállítása
    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
