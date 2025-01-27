package com.example.demo.Application.Service;

import com.example.demo.Application.Model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image saveImage(Image image);
    Image getImageById(Long id);
    List<Image> getAllImages();
    Image createImage(Image image);
    Optional<Image> findByFilename(String filename);
    Image updateImage(Long id, Image image);
    void deleteImage(Long id);
}
