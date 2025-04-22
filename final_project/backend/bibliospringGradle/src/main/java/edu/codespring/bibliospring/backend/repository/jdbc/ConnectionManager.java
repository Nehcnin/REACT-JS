package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.utils.PropertyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionManager {
    private List<Connection> connections;

    private static ConnectionManager connectionManager;
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);

    private ConnectionManager(){
        connections = new LinkedList<>();
        try {
            Class.forName(PropertyProvider.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        for(int i=0;i<10;i++){
            try {
                connections.add(DriverManager.getConnection(PropertyProvider.getProperty("JDBC_URL"),PropertyProvider.getProperty("DB_USER"),PropertyProvider.getProperty("DB_PASSWORD")));
            } catch (SQLException e) {
                LOG.error("Error connectiong to database",e);
                throw new RepositoryException("Error connectiong to database",e);
            }
        }

    }
    public static synchronized ConnectionManager getInstance(){
        if(connectionManager == null){
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public Connection getConnection(){
        if(!connections.isEmpty()){
            return connections.remove(0);
        }
        return null;
    }

    public void returnConnection(Connection connection){
        connections.add(connection);
    }
}
