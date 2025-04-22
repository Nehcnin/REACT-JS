package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.service.impl.BookServiceImpl;
import edu.codespring.bibliospring.backend.service.impl.UserServiceImpl;

public class ServiceFactory {
    public static UserService getUserService(){
            return new UserServiceImpl();
    };
    public static BookService getBookService(){
            return new BookServiceImpl();
    };
}
