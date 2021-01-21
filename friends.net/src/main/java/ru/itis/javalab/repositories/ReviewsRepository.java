package ru.itis.javalab.repositories;

import ru.itis.javalab.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends CrudRepository<Review> {
    public Optional<List<Review>> findAllByEventId(int id);

}
