package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserRepository implements UserRepository {
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUserRepository.class);
    @Override
    public User create(User user) {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO User(UID, username, password) VALUES(?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,user.getUuid());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            ps.execute();

            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            user.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOG.error("Error creating user",e);
            throw new RepositoryException("Error creating user",e);
        } finally {
            if(connection != null){
                connectionManager.returnConnection(connection);
            }
        }
        return user;
    }

    @Override
    public User getById(Long id) {
        Connection con = connectionManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE ID=?");
            ps.setLong(1,id);
            ResultSet rs =ps.executeQuery();
            rs.next();

            User user = new User();
            user.setUuid(rs.getString("UID"));
            user.setId(rs.getLong("ID"));
            user.setUsername("username");
            user.setPassword("password");
            return user;
        } catch (SQLException e) {
            LOG.error("Error getting user",e);
            throw new RepositoryException("Error getting user",e);
        } finally {
            if(con != null){
                connectionManager.returnConnection(con);
            }
        }

    }

    @Override
    public void update(User user) {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE User SET username = ?, password = ? WHERE ID = ?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error updating user",e);
            throw new RepositoryException("Error updating user",e);
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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM User WHERE ID = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error deleting user",e);
            throw new RepositoryException("Error deleting user",e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public List<User> getAll() {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User");
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUuid(rs.getString("UID"));
                user.setId(rs.getLong("ID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            LOG.error("Error getting all users",e);
            throw new RepositoryException("Error getting all users",e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public User getByUsername(String username) {
        Connection con = connectionManager.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE username=?;");
            ps.setString(1,username);
            ResultSet rs =ps.executeQuery();
            rs.next();

            User user = new User();
            user.setUuid(rs.getString("UID"));
            user.setId(rs.getLong("ID"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (SQLException e) {
            LOG.error("Error getting user",e);
            throw new RepositoryException("Error getting user",e);
        } finally {
            if(con != null){
                connectionManager.returnConnection(con);
            }
        }
    }
}
