package ru.itis.javalab.repositories;

import ru.itis.javalab.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestsRepository extends CrudRepository<Request> {
    public Optional<List<Request>> findAllByEventId(int id);

    public void delete(int event_id, int user_id);

    public List<Request> findAllByUserId(int id);
}
