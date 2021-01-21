package ru.itis.javalab.services;

import ru.itis.javalab.dto.EventDto;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.repositories.EventsRepository;

import java.util.List;

public class EventsServiceImpl implements EventsService {

    private EventsRepository eventsRepository;

    public EventsServiceImpl(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Event findRandomEvent() {
        return eventsRepository.findRandomEvent();
    }

    @Override
    public List<Event> findByUserId(int id) {
        return eventsRepository.findByUserId(id);
    }

    @Override
    public void insert(Event event) {
        eventsRepository.insert(event);
    }

    @Override
    public Event findById(int id) {
        return eventsRepository.findById(id);
    }

    @Override
    public List<Event> findByPage(int page, int size) {
        return eventsRepository.findByPage(page, size);
    }

    @Override
    public List<Event> findAll() {
        return eventsRepository.findAll();
    }

    @Override
    public List<Event> findByNameAndCategory(String name, List<Integer> categories) {
        return eventsRepository.findByNameAndCategory(name, categories);
    }

    @Override
    public List<Event> findByNameAndCategoryAndDate(String eventName, List<Integer> categories, String date) {
        return eventsRepository.findByNameAndCategoryAndDate(eventName, categories, date);
    }

    @Override
    public List<Event> findByName(String eventName) {
        return eventsRepository.findByName(eventName);
    }

    @Override
    public List<Event> findByNameAndDate(String eventName, String eventDate) {
        return eventsRepository.findByNameAndDate(eventName, eventDate);
    }

    @Override
    public List<Event> findByDate(String eventDate) {
        return eventsRepository.findByDate(eventDate);
    }

    @Override
    public void update(Event event) {
        eventsRepository.update(event);
    }


}
