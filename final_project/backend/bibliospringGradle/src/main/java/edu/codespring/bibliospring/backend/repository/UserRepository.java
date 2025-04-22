package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.model.User;

import java.util.List;

public interface UserRepository {
    public User create(User user) throws RepositoryException;
    public User getById(Long id)throws RepositoryException;
    public void update(User user)throws RepositoryException;
    public void delete(Long id)throws RepositoryException;
    public List<User> getAll()throws RepositoryException;
    public User getByUsername(String username)throws RepositoryException;
}
