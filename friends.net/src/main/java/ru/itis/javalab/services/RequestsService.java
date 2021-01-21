package ru.itis.javalab.services;

import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Request;

import java.util.List;

public interface RequestsService {
    public List<Request> findAllByEventId(int id);

    public void delete(int event_id, int user_id);

    public void insert(Request adr);

    public List<Event> findAllByUserId(int id);
}
