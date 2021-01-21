package ru.itis.javalab.repositories;

import ru.itis.javalab.model.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    public User authenticateUser(User user);

    Optional<User> findByUUID(String uuid);

    void updateUUID(User user);

    public User findByName(String name);
}
