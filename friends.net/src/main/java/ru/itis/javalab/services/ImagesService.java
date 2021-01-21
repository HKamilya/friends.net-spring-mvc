package ru.itis.javalab.services;

import ru.itis.javalab.model.Image;

public interface ImagesService {

    public void insert(Image adr);

    public Image findById(int id);

    public Image findByAddress(String address);

    public void update(Image adr);

}
