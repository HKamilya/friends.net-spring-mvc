package ru.itis.javalab.services;

import ru.itis.javalab.dto.EventDto;
import ru.itis.javalab.model.Event;

import java.util.List;

public interface EventsService {
    public Event findRandomEvent();

    public List<Event> findByUserId(int id);

    public void insert(Event event);

    public Event findById(int id);

    public List<Event> findByPage(int page, int size);

    public List<Event> findAll();

    public List<Event> findByNameAndCategory(String name, List<Integer> categories);

    public List<Event> findByNameAndCategoryAndDate(String eventName, List<Integer> categories, String date);

    public List<Event> findByName(String eventName);

    public List<Event> findByNameAndDate(String eventName, String eventDate);

    public List<Event> findByDate(String eventDate);

    void update(Event event);
}
