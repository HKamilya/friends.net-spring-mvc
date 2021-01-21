package ru.itis.javalab.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Categories {
    private Integer id;
    private String name;
    private String description;


}