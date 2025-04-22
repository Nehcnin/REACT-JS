package edu.codespring.bibliospring.backend.service.impl;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.repository.memory.RepositoryFactory;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.UserService;
import edu.codespring.bibliospring.backend.utils.PasswordEncrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = RepositoryFactory.getInstance().getUserRepository();

    public static final Logger LOG= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User register(User user) {
        try {
            user.setPassword(PasswordEncrypter.generateHashPassword(user.getPassword(),user.getUuid()));
            return userRepository.create(user);
        }
        catch (RepositoryException e){
            LOG.error("Error registering user");
            throw new ServiceException("Error registering user",e);
        }
    }

    @Override
    public boolean login(User user) {
        try {
            User dbUser = userRepository.getByUsername(user.getUsername());
            if(dbUser != null){
                user.setPassword(PasswordEncrypter.generateHashPassword(user.getPassword(),dbUser.getUuid()));
                return dbUser.getPassword().equals(user.getPassword());
            }
        }
        catch (RepositoryException e){
            LOG.error("Error finding user");
            throw new ServiceException("Error finding user",e);
        }

        return false;
    }
}
