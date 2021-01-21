package ru.itis.javalab.services;

import ru.itis.javalab.model.User;

import java.util.Optional;

public interface UsersService {
    public void update(User user);

    public User findById(int id);

    public void insert(User user);

    public User findByName(String name);

    public User authenticateUser(User user);

    Optional<User> findByUUID(String uuid);

    void updateUUID(User user);
}
