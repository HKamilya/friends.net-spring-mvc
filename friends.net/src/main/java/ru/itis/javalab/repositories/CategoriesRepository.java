package ru.itis.javalab.repositories;

import ru.itis.javalab.model.Categories;

import java.util.List;

public interface CategoriesRepository extends CrudRepository<Categories> {

    public List<Categories> findAll();

    public Categories findById(int id);
}
