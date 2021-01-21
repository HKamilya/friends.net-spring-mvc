package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Request;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.RequestsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class UserEventsController {

    @Autowired
    private EventsService eventsService;
    @Autowired
    private RequestsService requestsService;

    @RequestMapping(value = "/UserEvents", method = RequestMethod.GET)
    public ModelAndView getUserEvents(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        List<Event> eventList = eventsService.findByUserId(user.getId());
        LinkedHashMap<Event, List<Request>> evReqList = new LinkedHashMap<>();
        for (Event event : eventList) {
            List<Request> requests = requestsService.findAllByEventId(event.getId());
            evReqList.put(event, requests);
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("evReqList", evReqList);
        modelAndView.setViewName("userEvents_view");
        return modelAndView;
    }

    @RequestMapping(value = "/UserEvents", method = RequestMethod.POST)
    public ModelAndView postUserEvents(HttpServletRequest request) throws UnsupportedEncodingException {
//        request.setCharacterEncoding("UTF-8");
        String status = "";
        int status_id = Integer.parseInt(request.getParameter("status"));
        if (status_id == 1) {
            status = "актуально";
        } else {
            status = "неактуально";
        }
        int event_id = Integer.parseInt(request.getParameter("event_id"));

        Event event = new Event();
        event.setId(event_id);
        event.setStatus(status);
        eventsService.update(event);
        return new ModelAndView("redirect:/UserEvents");
    }
}
