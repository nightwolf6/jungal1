package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.TourPackage;
import com.example.demo.Application.Service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tour-packages")
public class TourPackageController {

    private final TourPackageService tourPackageService;

    @Autowired
    public TourPackageController(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }

    @GetMapping
    public ResponseEntity<List<TourPackage>> getAllTourPackages() {
        List<TourPackage> tourPackages = tourPackageService.getAllTourPackages();
        return ResponseEntity.ok(tourPackages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourPackage> getTourPackageById(@PathVariable Long id) {
        Optional<TourPackage> tourPackage = tourPackageService.getTourPackageById(id);
        return tourPackage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createTourPackage(@RequestBody TourPackage tourPackage) {
        TourPackage createdTourPackage = tourPackageService.createTourPackage(tourPackage);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tour Package created successfully: " + createdTourPackage.getPackageId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTourPackage(@PathVariable Long id, @RequestBody TourPackage tourPackage) {
        tourPackage.setPackageId(id);
        TourPackage updatedTourPackage = tourPackageService.updateTourPackage(tourPackage);
        return ResponseEntity.ok("Tour Package updated successfully: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTourPackage(@PathVariable Long id) {
        tourPackageService.deleteTourPackage(id);
        return ResponseEntity.ok("Tour Package deleted successfully: " + id);
    }
}
