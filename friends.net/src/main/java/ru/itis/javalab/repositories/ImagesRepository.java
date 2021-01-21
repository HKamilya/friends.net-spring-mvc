package ru.itis.javalab.repositories;

import ru.itis.javalab.model.Image;

public interface ImagesRepository extends CrudRepository<Image> {
    public Image findByAddress(String address);
}
