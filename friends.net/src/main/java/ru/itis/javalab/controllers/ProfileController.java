package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Image;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.ImagesService;
import ru.itis.javalab.services.RequestsService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private ImagesService imagesService;
    @Autowired
    private RequestsService requestsService;


    @RequestMapping(value = "/Profile", method = RequestMethod.GET)
    public ModelAndView getProfilePage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        modelAndView.addObject("user", user);

        Image image = imagesService.findById(user.getImage().getId());
        List<Event> eventsEv = requestsService.findAllByUserId(user.getId());
        List<Event> events = new ArrayList<>();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        String date = formatForDateNow.format(dateNow);
        System.out.println(user.getId());
        for (Event event : eventsEv) {
            if (event.getDate().compareTo(date) >= 0) {
                events.add(event);
            }
        }
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("fullName", user.getFullname());
        modelAndView.addObject("description", user.getDescription());
        modelAndView.addObject("list", events);
        modelAndView.addObject("image", image);
        modelAndView.setViewName("user_view");
        return modelAndView;
    }

    @RequestMapping(value = "/Profile", method = RequestMethod.POST)
    public String postDataOnProfilePage(@RequestParam(value = "event_id") int event_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        requestsService.delete(event_id, user.getId());
        return "redirect:/Profile";
    }
}
