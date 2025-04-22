package edu.codespring.bibliospring.backend.repository.memory;

import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.UserRepository;

public class MemoryRepositoryFactory extends RepositoryFactory{

    @Override
    public UserRepository getUserRepository() {
        return new MemoryUserRepository();
    }

    @Override
    public BookRepository getBookRepository() {
        return null;
    }
}
