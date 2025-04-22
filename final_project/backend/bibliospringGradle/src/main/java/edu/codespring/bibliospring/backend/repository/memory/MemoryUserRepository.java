package edu.codespring.bibliospring.backend.repository.memory;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryUserRepository implements UserRepository {
    private ConcurrentHashMap<Long, User> users;
    private AtomicLong idGenerator;

    public MemoryUserRepository() {
        users = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong();
    }
    @Override
    public User create(User user) {
        user.setId(idGenerator.incrementAndGet());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername()
                        .equals(username))
                .findAny()
                .orElse(null);
    }
}
