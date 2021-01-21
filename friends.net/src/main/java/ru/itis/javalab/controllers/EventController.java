package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Request;
import ru.itis.javalab.model.Review;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.RequestsService;
import ru.itis.javalab.services.ReviewsService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventsService eventsService;
    @Autowired
    private RequestsService requestsService;
    @Autowired
    private ReviewsService reviewsService;

    @RequestMapping(value = "/Event", method = RequestMethod.GET)
    public ModelAndView getEventPage(@RequestParam(value = "id") Integer id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Event event = eventsService.findById(id);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = formatForDateNow.format(dateNow);
        List<Request> requests = requestsService.findAllByEventId(id);
        int result = event.getDate().compareTo(currDate);
        List<Review> reviews = reviewsService.findAllByEventId(id);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        modelAndView.addObject("user", user);
        modelAndView.addObject("event", event);
        modelAndView.addObject("numOfReq", requests.size());
        modelAndView.addObject("currDate", currDate);
        modelAndView.addObject("diff", result);
        modelAndView.addObject("reviewsList", reviews);
        modelAndView.setViewName("event_view");
        return modelAndView;
    }
}
