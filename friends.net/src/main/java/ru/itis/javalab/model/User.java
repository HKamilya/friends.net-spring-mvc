package ru.itis.javalab.model;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Integer id;
    private String fullname;
    private String email;
    private String username;
    private String password;
    private Image image;
    private String description;
    private String UUID;
}