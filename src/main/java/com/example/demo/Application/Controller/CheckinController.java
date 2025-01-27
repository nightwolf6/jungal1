package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.Checkin;
import com.example.demo.Application.Service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkins")

public class CheckinController {

    private final CheckinService checkinService;

    @Autowired
    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    // Crear un nuevo check-in
    @PostMapping
    public ResponseEntity<Checkin> createCheckin(@RequestBody Checkin checkin) {
        Checkin createdCheckin = checkinService.createCheckin(checkin);
        return ResponseEntity.ok(createdCheckin);
    }

    // Obtener un check-in por ID
    @GetMapping("/{id}")
    public ResponseEntity<Checkin> getCheckinById(@PathVariable Long id) {
        Optional<Checkin> checkin = checkinService.getCheckinById(id);
        if (checkin.isPresent()) {
            return ResponseEntity.ok(checkin.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Verificar si el reconocimiento facial fue aprobado para un check-in
    @GetMapping("/{id}/facial-recognition-status")
    public ResponseEntity<Boolean> facialRecognitionApproved(@PathVariable Long id) {
        try {
            boolean isApproved = checkinService.facialRecognitionApproved(id);
            return ResponseEntity.ok(isApproved);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar el estado del reconocimiento facial de un check-in
    @PutMapping("/{id}/facial-recognition-status")
    public ResponseEntity<Void> updateFacialRecognitionStatus(
            @PathVariable Long id,
            @RequestParam boolean approved) {
        try {
            checkinService.updateFacialRecognitionStatus(id, approved);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un check-in por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckin(@PathVariable Long id) {
        checkinService.deleteCheckin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Checkin>> getAllCheckins() {
        // Ahora, solo se gestionan las peticiones sin verificar el rol en el backend.
        List<Checkin> checkins = checkinService.getAllCheckins();
        return ResponseEntity.ok(checkins);
    }

}
