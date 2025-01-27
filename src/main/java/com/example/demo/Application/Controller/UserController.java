package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.User;
import com.example.demo.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.assignRole(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.removeRole(userId, roleId);
        return ResponseEntity.ok().build();
    }

/*    @GetMapping("/{id}/photo")
    public ResponseEntity<String> obtenerImagenBase64(@PathVariable Long id) {
        try {
            String imagenBase64 = userService.obtenerImagenBase64(id);
            return ResponseEntity.ok(imagenBase64);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

/*    @GetMapping("/{id}/imageIdentifier")
    public ResponseEntity<String> getImageIdentifier(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user.getImageIdentifier()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }*/


    @GetMapping("/{id}/imageIdentifier")
    public ResponseEntity<Map<String, String>> getImageIdentifier(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    // Crear un mapa con el identificador
                    Map<String, String> response = new HashMap<>();
                    response.put("imageIdentifier", user.getImageIdentifier());
                    return ResponseEntity.ok(response); // Enviar el mapa como JSON
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/image-identifier/{imageIdentifier}")
    public ResponseEntity<byte[]> getImageByIdentifier(@PathVariable String imageIdentifier) {
        try {
            byte[] image = userService.getImageDataByIdentifier(imageIdentifier);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/jpeg");
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}