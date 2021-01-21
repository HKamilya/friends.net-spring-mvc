package ru.itis.javalab.services;

import ru.itis.javalab.model.Review;
import ru.itis.javalab.repositories.ReviewsRepository;

import java.util.List;
import java.util.Optional;

public class ReviewsServiceImpl implements ReviewsService {
    private ReviewsRepository reviewsRepository;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public List<Review> findAllByEventId(int id) {
        Optional<List<Review>> reviews = reviewsRepository.findAllByEventId(id);
        return reviews.orElse(null);

    }

    @Override
    public void insert(Review review) {
        reviewsRepository.insert(review);
    }
}
