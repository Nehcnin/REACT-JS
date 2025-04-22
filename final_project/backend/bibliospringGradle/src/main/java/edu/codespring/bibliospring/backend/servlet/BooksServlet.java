package edu.codespring.bibliospring.backend.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.jdbc.JdbcBookRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@WebServlet("/books")
public class BooksServlet extends HttpServlet {
    private final BookRepository bookRepository = new JdbcBookRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(BooksServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS fejlécek beállítása
        setCorsHeaders(resp);

        resp.setContentType("application/json");

        List<Book> books = null;
        try {
            books = bookRepository.getAll();
        } catch (RepositoryException e) {
            LOG.error("Error while getting all books", e);
        }

        String json = objectMapper.writeValueAsString(books);
        resp.getWriter().write(json);
    }

    // CORS fejlécek beállítása
    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
