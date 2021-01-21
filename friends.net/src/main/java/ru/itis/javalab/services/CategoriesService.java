package ru.itis.javalab.services;

import ru.itis.javalab.model.Categories;

import java.util.List;

public interface CategoriesService {


    public Categories findById(int id);


    public List<Categories> findAll();
}
