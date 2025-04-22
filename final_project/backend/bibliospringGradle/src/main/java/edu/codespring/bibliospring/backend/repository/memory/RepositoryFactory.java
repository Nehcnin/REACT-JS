package edu.codespring.bibliospring.backend.repository.memory;

import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.jdbc.JdbcRepositoryFactory;

public abstract class RepositoryFactory {
    public static RepositoryFactory getInstance(){
        return new JdbcRepositoryFactory();
    }
    public abstract UserRepository getUserRepository();
    public abstract BookRepository getBookRepository();
}
