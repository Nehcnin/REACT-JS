package edu.codespring.bibliospring.backend.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.service.BookService;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static edu.codespring.bibliospring.backend.servlet.UserServlet.servletJsonInit;
@WebServlet("/edit")
public class EditBookServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EditBookServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);

        JSONObject json = servletJsonInit(req, resp);

        String oldTitle = json.optString("oldTitle", "");
        String newTitle = json.optString("newTitle", "");
        String category = json.optString("category", "");
        String authors = json.optString("authors", "");

        LOG.info("Attempting to edit book: {}", oldTitle);

        try {
            BookService bookService = ServiceFactory.getBookService();

            Book updatedBook = new Book();
            updatedBook.setTitle(newTitle);
            updatedBook.setCategory(category);
            updatedBook.setAuthors(authors);

            bookService.updateBook(oldTitle, updatedBook);

        } catch (ServiceException e) {
            LOG.error("Service exception while updating book '{}': {}", oldTitle, e.getMessage());
            resp.getWriter().write("{\"status\": \"error\"}");
        }
        resp.getWriter().write("{\"status\": \"success\"}");
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
