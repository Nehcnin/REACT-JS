package edu.codespring.bibliospring.backend.service.impl;

import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.memory.RepositoryFactory;
import edu.codespring.bibliospring.backend.service.BookService;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.UserService;
import edu.codespring.bibliospring.backend.utils.PasswordEncrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository = RepositoryFactory.getInstance().getBookRepository();

    public static final Logger LOG= LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public Book insert(Book book) {
        try {
            return bookRepository.create(book);
        }
        catch (RepositoryException e){
            LOG.error("Error inserting book");
            throw new ServiceException("Error inserting book",e);
        }
    }

    @Override
    public boolean deleteByTitle(String title) {
        try {
            Book book = bookRepository.getByTitle(title);
            if(book != null){
                bookRepository.delete(book.getId());
                return true;
            }
        }
        catch (RepositoryException e){
            LOG.error("Error deleting book");
            throw new ServiceException("Error deleting book",e);
        }

        return false;
    }

    @Override
    public void updateBook(String oldTitle, Book newBook) {
        Book book = bookRepository.getByTitle(oldTitle);
        if(!newBook.getCategory().isEmpty())
            book.setCategory(newBook.getCategory());
        if(!newBook.getAuthors().isEmpty())
            book.setAuthors(newBook.getAuthors());
        if(!newBook.getTitle().isEmpty())
            book.setTitle(newBook.getTitle());

        LOG.info("Old title :"+oldTitle+"new book: "+book.getTitle()+" "+book.getCategory()+" "+book.getAddedBy() + " "+book.getAuthors());


        bookRepository.update(book);
    }
}
