package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Review;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.RequestsService;
import ru.itis.javalab.services.ReviewsService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AddReviewController {
    @Autowired
    private ReviewsService reviewsService;
    @Autowired
    private EventsService eventsService;

    @RequestMapping(value = "/AddReview", method = RequestMethod.POST)
    public String addReview(@RequestParam(value = "review") String rev,
                            @RequestParam(value = "event_id") int event_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        Review review = new Review();
        Event event = eventsService.findById(event_id);

        review.setEvent_id(event);
        review.setText(rev);
        review.setUser_id(user);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        review.setDate(formatForDateNow.format(dateNow));
        reviewsService.insert(review);

        return "redirect:/Event?id=" + event_id;
    }
}
