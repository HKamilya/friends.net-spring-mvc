package ru.itis.javalab.model;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class Event {
    private Integer id;
    private User user_id;
    private String name;
    private String city;
    private String street;
    private String house;
    private String date;
    private Image image;
    private String description;
    private Categories category_id;
    private String time;
    private String status;

}
