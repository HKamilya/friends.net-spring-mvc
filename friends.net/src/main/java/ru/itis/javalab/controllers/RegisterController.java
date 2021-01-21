package ru.itis.javalab.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Image;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.ImagesService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class RegisterController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ImagesService imagesService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public final String UPLOAD_DIR = "C:\\Users\\gipot\\Desktop\\inf\\friends.net\\friends.net\\data";


    @RequestMapping(value = "/Registration", method = RequestMethod.GET)
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register_view");
        return modelAndView;
    }

    @RequestMapping(value = "/Registration", method = RequestMethod.POST)
    public String postRegisterInfo(@RequestParam(value = "fullname") String fullName,
                                   @RequestParam(value = "email") String email,
                                   @RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password, HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register_view");

        boolean passMatcher = password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");

        boolean userNMMatcher = username.matches("^[a-zA-Z](.[a-zA-Z0-9_-]*)");
        if (!passMatcher | !userNMMatcher) {
            modelAndView.addObject("errMessage", "Введите корректные данные");
            return "register_view";
        } else {


            User users = new User();
            String encodePass = passwordEncoder.encode(password);
            users.setFullname(fullName);
            users.setEmail(email);
            users.setUsername(username);
            users.setPassword(encodePass);
            Image image = new Image();
            image.setType("image/png");
            image.setAddress(username + "man.png");
            try (InputStream in = new URL(UPLOAD_DIR + "/man.png").openStream()) {
                Files.copy(in, Paths.get(UPLOAD_DIR + image.getAddress()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            imagesService.insert(image);
            image = imagesService.findByAddress(image.getAddress());

            users.setImage(image);

            usersService.insert(users);
            HttpSession session = request.getSession();
            User user = usersService.findByName(username);
            session.setMaxInactiveInterval(10 * 60);
            session.setAttribute("User", user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("username", user.getUsername());
            modelAndView.addObject("fullName", user.getFullname());
            modelAndView.addObject("username", user.getUsername());
            modelAndView.addObject("fullname", user.getFullname());
            modelAndView.addObject("description", user.getDescription());
            modelAndView.addObject("image", user.getImage());

        }
        return "user_view";
    }
}
