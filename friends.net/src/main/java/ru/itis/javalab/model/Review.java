package ru.itis.javalab.model;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Review {
    private Event event_id;
    private User user_id;
    private String text;
    private String date;

}
