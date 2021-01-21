package ru.itis.javalab.services;

import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Request;
import ru.itis.javalab.repositories.RequestsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestsServiceImpl implements RequestsService {
    private RequestsRepository requestsRepository;

    public RequestsServiceImpl(RequestsRepository requestsRepository) {
        this.requestsRepository = requestsRepository;
    }

    @Override
    public List<Request> findAllByEventId(int id) {
        Optional<List<Request>> requests = requestsRepository.findAllByEventId(id);
        return requests.orElse(null);
    }

    public List<Event> findAllByUserId(int id) {
        List<Request> requests = requestsRepository.findAllByUserId(id);
        List<Event> events = new ArrayList<>();
        for (Request request : requests) {
            events.add(request.getEvent_id());
        }
        return events;
    }

    @Override
    public void delete(int event_id, int user_id) {
        requestsRepository.delete(event_id, user_id);
    }

    @Override
    public void insert(Request adr) {
        requestsRepository.insert(adr);
    }
}
