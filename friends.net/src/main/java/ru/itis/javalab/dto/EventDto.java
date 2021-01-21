package ru.itis.javalab.dto;

import lombok.*;
import ru.itis.javalab.model.Categories;
import ru.itis.javalab.model.User;
import javax.servlet.http.Part;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class EventDto {
    private String name;
    private String city;
    private String street;
    private String house;
    private String date;
    private String time;
    private Part filePart;
    private String description;
    private int category_id;
}
