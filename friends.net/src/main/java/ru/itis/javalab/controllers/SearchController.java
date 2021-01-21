package ru.itis.javalab.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.itis.javalab.model.Categories;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.CategoriesService;
import ru.itis.javalab.services.EventsService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private EventsService eventsService;
    @Autowired
    private CategoriesService categoriesService;

    @RequestMapping(value = "/Search", method = RequestMethod.GET)
    public ModelAndView getSearchPage(HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        List<Categories> categories = categoriesService.findAll();
        List<String> eventsNames = new ArrayList<>();
        List<Event> eventList = eventsService.findAll();
        for (Event ev : eventList) {
            eventsNames.add(ev.getName());
        }
        eventList.sort((o1, o2) -> (o2.getDate().compareTo(o1.getDate())));

        ObjectMapper objectMapper = new ObjectMapper();


        String json = objectMapper.writeValueAsString(eventsNames);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        modelAndView.addObject("user", user);
        modelAndView.addObject("names", json);
        modelAndView.addObject("catList", categories);
        modelAndView.addObject("list", eventList);
        modelAndView.setViewName("search_view");
        return modelAndView;
    }

    @RequestMapping(value = "/Search", method = RequestMethod.POST)
    public void getSearchEvent(@RequestParam(value = "search") String search,
                               @RequestParam(value = "tags") String s,
                               @RequestParam(value = "date") String date,
                               HttpServletResponse response, HttpServletRequest request) {
        System.out.println("tags" + s);
        List<Event> events = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        String[] tags;
        tags = s.split(",");
        if (!s.equals("[]")) {
            for (int i = 0; i < tags.length; i++) {
                tags[i] = tags[i].replace("[", "");
                tags[i] = tags[i].replace("\"", "");
                tags[i] = tags[i].replace("]", "");
            }
        }
        if (!s.equals("[]") & date.length() > 1) { //поиск по категориям, имени и дате или по категории и дате
            for (String tag : tags) {
                list.add(Integer.parseInt(tag));
            }
            events = eventsService.findByNameAndCategoryAndDate(search, list, date);
        } else if (!s.equals("[]") & date.length() < 1) { // поиск по категориям и  имени и просто категориям
            for (String tag : tags) {
                list.add(Integer.parseInt(tag));
            }
            events = eventsService.findByNameAndCategory(search, list);
        } else if (s.equals("[]") & search.length() > 0 & date.length() > 0) {
            events = eventsService.findByNameAndDate(search, date);
        } else if (date.length() < 1 & s.equals("[]")) {//поиск по имени
            events = eventsService.findByName(search);
        } else if (s.equals("[]") & search.length() == 0 & date.length() > 1) {
            events = eventsService.findByDate(date);
        }
        if (events.size() == 0) {
            request.setAttribute("message", "   К сожалению, таких мероприятий нет");
        }
        response.setContentType("application/json");

        // String json = new Gson().toJson(events);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        try {
            String json = objectMapper.writeValueAsString(events);
            response.getWriter().write(json);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
