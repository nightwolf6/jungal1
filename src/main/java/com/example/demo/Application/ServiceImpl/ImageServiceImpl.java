package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.Image;
import com.example.demo.Application.Repository.ImageRepository;
import com.example.demo.Application.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> findByFilename(String filename) {
        return imageRepository.findByFilename(filename);
    }

    @Override
    public Image updateImage(Long id, Image image) {
        Image existingImage = imageRepository.findById(id).orElse(null);
        if (existingImage != null) {
            existingImage.setFilename(image.getFilename());
            existingImage.setFilepath(image.getFilepath());
            return imageRepository.save(existingImage);
        }
        return null;
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
