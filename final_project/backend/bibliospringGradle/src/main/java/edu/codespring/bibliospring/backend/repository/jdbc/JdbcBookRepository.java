package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepository implements BookRepository {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUserRepository.class);
    @Override
    public Book create(Book book) {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Book(title, category, authors, addedBy) VALUES(?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,book.getTitle());
            ps.setString(2,book.getCategory());
            ps.setString(3,book.getAuthors());
            ps.setString(4,book.getAddedBy());
            ps.execute();

            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            book.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOG.error("Error creating book "+book.getTitle()+book.getCategory(),e);
            throw new RepositoryException("Error creating book",e);
        } finally {
            if(connection != null){
                connectionManager.returnConnection(connection);
            }
        }
        return book;
    }

    @Override
    public Book getById(Long id) {
        Connection con = connectionManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Book WHERE ID=?");
            ps.setLong(1,id);
            ResultSet rs =ps.executeQuery();
            rs.next();

            Book book = new Book();
            book.setAddedBy(rs.getString("addedBy"));
            book.setCategory(rs.getString("category"));
            book.setId(rs.getLong("ID"));
            book.setTitle("title");
            book.setAuthors("authors");
            return book;
        } catch (SQLException e) {
            LOG.error("Error getting book",e);
            throw new RepositoryException("Error getting book",e);
        } finally {
            if(con != null){
                connectionManager.returnConnection(con);
            }
        }

    }

    @Override
    public void update(Book book) {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Book SET title = ?, category = ?, authors = ?, addedBy = ? WHERE ID = ?");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCategory());
            ps.setString(3, book.getAuthors());
            ps.setString(4, book.getAddedBy());
            ps.setLong(5,book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error updating book",e);
            throw new RepositoryException("Error updating book",e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Book WHERE ID = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error deleting book",e);
            throw new RepositoryException("Error deleting book",e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public List<Book> getAll() {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Book");
            ResultSet rs = ps.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setCategory(rs.getString("category"));
                book.setId(rs.getLong("ID"));
                book.setAuthors(rs.getString("authors"));
                book.setAddedBy(rs.getString("addedBy"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            LOG.error("Error getting all books",e);
            throw new RepositoryException("Error getting all books",e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public Book getByTitle(String title) {
        Connection con = connectionManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Book WHERE title=?;");
            ps.setString(1,title);
            ResultSet rs =ps.executeQuery();
            rs.next();

            Book book = new Book();
            book.setTitle(rs.getString("title"));
            book.setCategory(rs.getString("category"));
            book.setId(rs.getLong("ID"));
            book.setAuthors(rs.getString("authors"));
            book.setAddedBy(rs.getString("addedBy"));
            return book;
        } catch (SQLException e) {
            LOG.error("Error getting book",e);
            throw new RepositoryException("Error getting book",e);
        } finally {
            if(con != null){
                connectionManager.returnConnection(con);
            }
        }
    }
}
