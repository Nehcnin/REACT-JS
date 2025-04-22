package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;

import java.util.List;

public interface BookRepository {
    public Book create(Book book) throws RepositoryException;
    public Book getById(Long id)throws RepositoryException;
    public void update(Book book)throws RepositoryException;
    public void delete(Long id)throws RepositoryException;
    public List<Book> getAll()throws RepositoryException;
    public Book getByTitle(String title)throws RepositoryException;
}
