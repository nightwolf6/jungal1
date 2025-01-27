package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.Role;
import com.example.demo.Application.Model.User;
import com.example.demo.Application.Repository.UserRepository;
import com.example.demo.Application.Service.RoleService;
import com.example.demo.Application.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

/*    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }*/
    @Override
    public User saveUser(User user) {
        // Generar un identificador único si no está definido
        if (user.getImageIdentifier() == null || user.getImageIdentifier().isEmpty()) {
            user.setImageIdentifier(UUID.randomUUID().toString());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

/*    @Override
    public User createUser(User user) {
        // Implementar lógica para la creación de nuevos usuarios
        // Por ejemplo, asignar valores por defecto y validar datos
        return userRepository.save(user);
    }*/

    @Override
    public User createUser(User user) {
        if (user.getImageIdentifier() == null || user.getImageIdentifier().isEmpty()) {
            user.setImageIdentifier(UUID.randomUUID().toString()); // Genera un identificador único
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // Verifica si el usuario ya existe en la base de datos
        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        if (existingUserOptional.isPresent()) {
            // Si el usuario existe, actualiza sus datos
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEnabled(user.isEnabled());

            // Si deseas actualizar el rol, puedes hacerlo aquí
            // existingUser.setRole(user.getRole());

            // Guarda el usuario actualizado en la base de datos
            return userRepository.save(existingUser);
        } else {
            // Si el usuario no existe, lanza una excepción
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @Override
    public void enableUser(Long userId) {

    }

    @Override
    public void disableUser(Long userId) {
        // Implementar lógica para deshabilitar usuarios
    }

    @Override
    public void assignRole(Long userId, Long roleId) {
        // Implementa la lógica para asignar un rol a un usuario aquí
        // Puedes utilizar el servicio de RoleService para obtener el rol por su ID
        // y luego asignarlo al usuario correspondiente por su ID
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Role> roleOptional = roleService.getRoleById(roleId);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();

            // Asignar el rol al usuario
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario o rol no encontrado");
        }
    }

    @Override
    public void removeRole(Long userId, Long roleId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Role> roleOptional = roleService.getRoleById(roleId);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();

            // Verificar si el usuario tiene asignado este rol
            if (user.getRole() != null && user.getRole().equals(role)) {
                // Desasignar el rol del usuario
                user.setRole(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("El usuario no tiene asignado este rol");
            }
        } else {
            throw new RuntimeException("Usuario o rol no encontrado");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    // Otros métodos para la gestión de usuarios...

    @Override
    public String obtenerImagenBase64(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    byte[] photo = user.getPhoto();
                    if (photo != null) {
                        return java.util.Base64.getEncoder().encodeToString(photo);
                    } else {
                        throw new RuntimeException("El usuario no tiene una imagen registrada.");
                    }
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public byte[] getImageDataByIdentifier(String imageIdentifier) {
        // Busca al usuario por el identificador
        User user = userRepository.findByImageIdentifier(imageIdentifier)
                .orElseThrow(() -> new EntityNotFoundException("User not found with image identifier: " + imageIdentifier));

        // Retorna directamente los bytes de la foto almacenada en el campo 'photo'
        if (user.getPhoto() == null) {
            throw new IllegalArgumentException("No photo found for user with image identifier: " + imageIdentifier);
        }
        return user.getPhoto();
    }

}
