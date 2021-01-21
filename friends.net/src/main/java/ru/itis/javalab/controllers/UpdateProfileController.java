package ru.itis.javalab.controllers;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;


@Controller
public class UpdateProfileController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ImagesService imagesService;

    public final String UPLOAD_DIR = "C:\\Users\\gipot\\Desktop\\inf\\friends.net\\friends.net\\data";

    //TODO
    @RequestMapping(value = "/UpdateProfile", method = RequestMethod.POST)
    public String updateProfile(@RequestParam(value = "fullName") String fullname,
                                @RequestParam(value = "description") String description,
                                @RequestParam(value = "image") Part filePart,
                                HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String ext2 = FilenameUtils.getExtension(fileName);
        if (fileName.length() > 1) {
            String uploadDir = UPLOAD_DIR;
            String imgAddress =
                    UUID.randomUUID().toString() +
                            "-" +
                            filePart.getSubmittedFileName();
            IOUtils.copyLarge(
                    filePart.getInputStream(),
                    new FileOutputStream(uploadDir +
                            File.separator + imgAddress
                    )
            );
            Image image = user.getImage();
            image.setAddress(imgAddress);
            image.setType("image/" + ext2);
            imagesService.update(image);

            user.setImage(image);
        }


        user.setDescription(description);
        user.setFullname(fullname);
        usersService.update(user);


        return "redirect:/Profile";
    }

    @RequestMapping(value = "/UpdateProfile", method = RequestMethod.GET)
    protected ModelAndView doGet(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        modelAndView.addObject("user", user);
        modelAndView.addObject("image", user.getImage());
        modelAndView.addObject("password", user.getPassword());
        modelAndView.addObject("fullName", user.getFullname());
        modelAndView.addObject("description", user.getDescription());
        modelAndView.setViewName("updateProfile");
        return modelAndView;
    }
}
