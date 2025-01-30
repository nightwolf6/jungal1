package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.Image;
import com.example.demo.Application.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + File.separator + filename);
            Files.write(path, file.getBytes());

            Image image = new Image();
            image.setFilename(filename);
            image.setFilepath(path.toString());
            imageService.saveImage(image);

            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*   @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        if (image != null) {
            try {
                Path path = Paths.get(image.getFilepath());
                byte[] imageBytes = Files.readAllBytes(path);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Cambiar el tipo de contenido según el tipo de imagen
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<String> getImage(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        if (image != null) {
            // Devuelve la URL de Cloudinary directamente
            return new ResponseEntity<>(image.getFilepath(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @PostMapping("/registerExistingImages")
    public ResponseEntity<String> registerExistingImages() {
        try {
            File directory = new File(uploadDir);
            if (!directory.exists() || !directory.isDirectory()) {
                return new ResponseEntity<>("Directory does not exist", HttpStatus.BAD_REQUEST);
            }

            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                return new ResponseEntity<>("No files found in directory", HttpStatus.BAD_REQUEST);
            }

            for (File file : files) {
                if (file.isFile()) {
                    String filename = file.getName();
                    Path path = Paths.get(file.getAbsolutePath());

                    if(!imageService.findByFilename(filename).isPresent()) {
                        Image image = new Image();
                        image.setFilename(filename);
                        image.setFilepath(path.toString());
                        imageService.saveImage(image);
                    }
                }
            }

            return new ResponseEntity<>("Images registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        Image savedImage = imageService.saveImage(image);
        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image) {
        Image updatedImage = imageService.updateImage(id, image);
        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
    }

}