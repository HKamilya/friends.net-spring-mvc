package ru.itis.javalab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.EventsService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AllEventsController {

    @Autowired
    private EventsService eventsService;

    @RequestMapping(value = "/AllEvents", method = RequestMethod.GET)
    public ModelAndView getAllEventsPage(HttpServletRequest request, @RequestParam(value = "page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        List<Event> eventList = eventsService.findByPage(page, 5);
        HttpSession session = request.getSession();
        session.setAttribute("page", page);
        List<Event> events = new ArrayList<>();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        String date = formatForDateNow.format(dateNow);

        for (Event event : eventList) {
            if (event.getDate().compareTo(date) >= 0) {
                events.add(event);
            }
        }
        User user = (User) session.getAttribute("User");
        modelAndView.addObject("page", page);
        modelAndView.addObject("user", user);
        modelAndView.addObject("list", events);
        modelAndView.setViewName("allEvents_view");
        return modelAndView;
    }

    @RequestMapping(value = "/AllEvents", method = RequestMethod.POST)
    public void getPrevOrNextPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "title") String title) {
        HttpSession session = request.getSession();
        int page = (int) session.getAttribute("page");
        if (title.equals("prev")) {
            page--;
        }
        if (title.equals("next")) {
            page++;
        }
        List<Event> eventList = eventsService.findByPage(page, 5);
        List<Event> events = new ArrayList<>();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        String date = formatForDateNow.format(dateNow);
        session.setAttribute("page", page);
        for (Event event : eventList) {
            if (event.getDate().compareTo(date) >= 0) {
                events.add(event);
            }
        }
        response.setContentType("application/json");
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
