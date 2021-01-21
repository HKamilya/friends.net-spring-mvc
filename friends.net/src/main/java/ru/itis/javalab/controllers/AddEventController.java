package ru.itis.javalab.controllers;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.dto.EventDto;
import ru.itis.javalab.model.Categories;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.model.Image;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.CategoriesService;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.ImagesService;


import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@MultipartConfig
@Controller
public class AddEventController {
    public final String UPLOAD_DIR = "C:\\Users\\gipot\\Desktop\\inf\\friends.net\\friends.net\\data";

    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private ImagesService imagesService;

    //TODO
    @RequestMapping(value = "/AddEvent", method = RequestMethod.POST)
    public String addEvent(@RequestBody EventDto eventDto,
                           HttpServletRequest request) throws IOException {
        System.out.println(eventDto.getName());

        String fileName = Paths.get(eventDto.getFilePart().getSubmittedFileName()).getFileName().toString();
        String ext2 = FilenameUtils.getExtension(fileName);
        String imgAddress =
                UUID.randomUUID().toString() +
                        "-" +
                        eventDto.getFilePart().getSubmittedFileName();
        IOUtils.copyLarge(
                eventDto.getFilePart().getInputStream(),
                new FileOutputStream(UPLOAD_DIR +
                        File.separator + imgAddress
                )
        );
        Image image = new Image();
        image.setType("image/" + ext2);
        image.setAddress(imgAddress);
        imagesService.insert(image);
        imagesService.insert(image);
        image = imagesService.findByAddress(imgAddress);
        String status = "актуально";


        Event event = new Event();
        Categories categories = new Categories();
        categories.setId(eventDto.getCategory_id());
        event.setName(eventDto.getName());
        event.setCity(eventDto.getCity());
        event.setTime(eventDto.getTime());
        event.setStreet(eventDto.getStreet());
        event.setHouse(eventDto.getHouse());
        event.setImage(image);
        event.setDescription(eventDto.getDescription());
        event.setCategory_id(categories);
        event.setStatus(status);
        event.setDate(eventDto.getDate());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        event.setUser_id(user);

        eventsService.insert(event);

        eventsService.insert(event);
        return "redirect:/AddEvent";
    }

    @RequestMapping(value = "/AddEvent", method = RequestMethod.GET)
    public ModelAndView getAddEventPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        List<Categories> catList = categoriesService.findAll();
        modelAndView.addObject("user", user);
        modelAndView.addObject("list", catList);
        modelAndView.setViewName("addEvent_view");
        return modelAndView;
    }
}
