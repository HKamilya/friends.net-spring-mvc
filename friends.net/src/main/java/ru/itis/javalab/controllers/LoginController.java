package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.RequestsService;
import ru.itis.javalab.services.UsersService;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RequestsService requestsService;

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login_view");
        return modelAndView;

    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "rememberMe") String remember, HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        boolean rememberMe = "true".equals(remember);
        System.out.println(rememberMe);
        User user = usersService.findByName(username);
        if (user.getUsername().equals(username)
                && passwordEncoder.matches(password, user.getPassword())) {
            if (rememberMe) {
                String uuid = UUID.randomUUID().toString();
                user.setUUID(uuid);
                usersService.updateUUID(user);
                Cookie auth = new Cookie("Auth", uuid);
                auth.setMaxAge(24 * 60 * 60 * 31);
                response.addCookie(auth);
            }
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("User", user);
            request.setAttribute("username", username);
            request.setAttribute("user", user);
            List<Event> events = requestsService.findAllByUserId(user.getId());

            modelAndView.addObject("image", user.getImage());
            modelAndView.addObject("list", events);
            modelAndView.addObject("description", user.getDescription());
            modelAndView.addObject("fullName", user.getFullname());
            modelAndView.setViewName("user_view");
        } else {
            request.setAttribute("errMessage", "Введены неправильные данные");
            modelAndView.setViewName("login_view");
        }
        return modelAndView;
    }
}
