package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;

public interface BookService {
    Book insert(Book book);

    boolean deleteByTitle(String title);
    void updateBook(String oldTitle, Book book);
}
