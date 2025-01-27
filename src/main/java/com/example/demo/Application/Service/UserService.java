package com.example.demo.Application.Service;

import com.example.demo.Application.Model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);

    User createUser(User user);

    User updateUser(User user);

    void enableUser(Long userId);

    void disableUser(Long userId);

    // Método para asignar un rol a un usuario
    void assignRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    User findByUsername(String username);

    String obtenerImagenBase64(Long userId);

    byte[] getImageDataByIdentifier(String imageIdentifier); // Método para obtener la imagen

}
