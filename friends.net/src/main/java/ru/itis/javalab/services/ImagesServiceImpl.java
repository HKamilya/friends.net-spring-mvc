package ru.itis.javalab.services;

import ru.itis.javalab.model.Image;
import ru.itis.javalab.repositories.ImagesRepository;

public class ImagesServiceImpl implements ImagesService {
    private ImagesRepository imagesRepository;

    public ImagesServiceImpl(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }


    @Override
    public void insert(Image adr) {
        imagesRepository.insert(adr);
    }

    public Image findByAddress(String address) {
        return imagesRepository.findByAddress(address);
    }

    @Override
    public Image findById(int id) {
        return imagesRepository.findById(id);
    }

    @Override
    public void update(Image adr) {
        imagesRepository.update(adr);
    }
}
