package edu.codespring.bibliospring.backend.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.jdbc.JdbcBookRepository;
import edu.codespring.bibliospring.backend.repository.jdbc.JdbcRepositoryFactory;
import edu.codespring.bibliospring.backend.service.BookService;
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

import static edu.codespring.bibliospring.backend.servlet.UserServlet.servletJsonInit;
@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final BookRepository bookRepository = new JdbcBookRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(BookServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS fejlécek beállítása
        setCorsHeaders(resp);

        JSONObject json = UserServlet.servletJsonInit(req, resp);
        String add = json.getString("add");

        if (add.equals("true")) {
            String title = json.optString("title", "");
            String category = json.optString("category", "");
            String authors = json.optString("authors", "");
            String addedBy = json.optString("username", "");

            Book book = new Book();
            book.setAddedBy(addedBy);
            book.setCategory(category);
            book.setAuthors(authors);
            book.setTitle(title);
            LOG.info("Received Book: " + title + " " + category + " " + authors + " " + addedBy);

            LOG.info("Trying to add book " + title + category + addedBy);
            try {
                BookService bookService = ServiceFactory.getBookService();
                book = bookService.insert(book);
            } catch (ServiceException e) {
                book = null;
            }

            if (book != null) {
                LOG.info("Book " + title + " added successfully");
                resp.getWriter().write("{\"status\": \"success\"}");
            } else {
                LOG.error("Error while adding the book " + title);
                resp.getWriter().write("{\"status\": \"error\"}");
            }

        } else {
            String title = json.optString("title", "");
            BookService bookService = ServiceFactory.getBookService();

            boolean deleted;
            LOG.info("Trying to delete book " + title);
            try {
                deleted = bookService.deleteByTitle(title);
            } catch (ServiceException e) {
                deleted = false;
            }

            if (deleted) {
                LOG.info("Book " + title + " deleted successfully");
                resp.getWriter().write("{\"status\": \"success\"}");
            } else {
                LOG.error("Error while deleting book " + title);
                resp.getWriter().write("{\"status\": \"error\"}");
            }
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
