package ru.itis.javalab.model;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class Image {
    private Integer id;
    private String type;
    private String address;

}
