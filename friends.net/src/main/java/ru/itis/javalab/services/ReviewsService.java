package ru.itis.javalab.services;

import ru.itis.javalab.model.Review;

import java.util.List;

public interface ReviewsService {
    public List<Review> findAllByEventId(int id);
    public void insert(Review review);
}
