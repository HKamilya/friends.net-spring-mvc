package ru.itis.javalab.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class Request {
    private Event event_id;
    private User subscriber_id;
    private String comment;

}
