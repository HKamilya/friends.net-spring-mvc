package ru.itis.javalab.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.javalab.model.Image;
import ru.itis.javalab.services.ImagesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class UploadImageController {
    @Autowired
    private ImagesService imagesService;
    public final String UPLOAD_DIR = "C:\\Users\\gipot\\Desktop\\inf\\friends.net\\friends.net\\data";

    @RequestMapping(value = "/img", method = RequestMethod.GET)
    public void getAllImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        Image image = imagesService.findById(id);

        response.setContentType(image.getType());

        IOUtils.copyLarge(
                new FileInputStream(UPLOAD_DIR + File.separator + image.getAddress()),
                response.getOutputStream()
        );
    }
}
