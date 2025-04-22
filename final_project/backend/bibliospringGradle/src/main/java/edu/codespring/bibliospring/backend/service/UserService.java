package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.model.User;

public interface UserService {
    User register(User user);

    boolean login(User user);
}
