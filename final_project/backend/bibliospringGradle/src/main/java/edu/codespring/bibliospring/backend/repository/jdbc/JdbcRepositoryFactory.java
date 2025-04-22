package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.memory.RepositoryFactory;

public class JdbcRepositoryFactory extends RepositoryFactory {
    @Override
    public UserRepository getUserRepository() {
        return new JdbcUserRepository();
    }
    public BookRepository getBookRepository() {
        return new JdbcBookRepository();
    }
}
