package ru.itis.javalab.repositories;

import java.util.List;

public interface CrudRepository<T> {
    public abstract void insert(T adr);

    public abstract T findById(int id);

    public abstract void update(T adr);

    public abstract List<T> findAll();

}
