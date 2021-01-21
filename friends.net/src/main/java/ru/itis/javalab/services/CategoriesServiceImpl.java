package ru.itis.javalab.services;

import ru.itis.javalab.model.Categories;
import ru.itis.javalab.repositories.CategoriesRepository;

import java.util.List;

public class CategoriesServiceImpl implements CategoriesService {
    private CategoriesRepository categoriesRepository;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public Categories findById(int id) {
        return categoriesRepository.findById(id);
    }

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

}
