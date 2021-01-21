package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Request;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.RequestsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SendRequestController {
    @Autowired
    private EventsService eventsService;
    @Autowired
    private RequestsService requestsService;

    @RequestMapping(value = "/SendRequest", method = RequestMethod.POST)
    public ModelAndView sendRequest(@RequestParam(value = "comment", required = false) String comment,
                                    @RequestParam(value = "event_id") int event_id,
                                    HttpServletRequest request) {
//        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        Request req = new Request();
        Event event = eventsService.findById(event_id);
        req.setEvent_id(event);
        req.setComment(comment);
        req.setSubscriber_id(user);
        requestsService.insert(req);
        return new ModelAndView("redirect:/Event?id=" + event_id);
    }
}
