package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AnProfileController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private EventsService eventsService;

    @RequestMapping(value = "/AnProfile", method = RequestMethod.GET)
    public ModelAndView getAnProfilePage(@RequestParam(value = "username") String username, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User anUser = usersService.findByName(username);
        List<Event> events = eventsService.findByUserId(anUser.getId());
        modelAndView.addObject("username", username);
        modelAndView.addObject("fullName", anUser.getFullname());
        modelAndView.addObject("description", anUser.getDescription());
        modelAndView.addObject("image", anUser.getImage());
        modelAndView.addObject("eventsList", events);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user_view");
        return modelAndView;

    }
}
