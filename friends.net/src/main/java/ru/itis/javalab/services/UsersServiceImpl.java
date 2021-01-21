package ru.itis.javalab.services;

import ru.itis.javalab.model.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.Optional;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void update(User user) {
        usersRepository.update(user);
    }

    @Override
    public User findById(int id) {
        return usersRepository.findById(id);
    }

    @Override
    public void insert(User user) {
        usersRepository.insert(user);
    }

    @Override
    public User findByName(String name) {
        return usersRepository.findByName(name);
    }

    @Override
    public User authenticateUser(User user) {
        return usersRepository.authenticateUser(user);
    }

    @Override
    public Optional<User> findByUUID(String uuid) {
        return usersRepository.findByUUID(uuid);
    }

    @Override
    public void updateUUID(User user) {
        usersRepository.updateUUID(user);
    }
}
