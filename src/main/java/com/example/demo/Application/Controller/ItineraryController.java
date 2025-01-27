package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.Itinerary;
import com.example.demo.Application.Service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping
    public ResponseEntity<List<Itinerary>> getAllItineraries() {
        List<Itinerary> itineraries = itineraryService.getAllItineraries();
        return ResponseEntity.ok(itineraries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> getItineraryById(@PathVariable Long id) {
        Optional<Itinerary> itinerary = itineraryService.getItineraryById(id);
        return itinerary.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tourPackage/{tourPackageId}")
    public ResponseEntity<List<Itinerary>> getItinerariesByTourPackageId(@PathVariable Long tourPackageId) {
        List<Itinerary> itineraries = itineraryService.getItinerariesByTourPackage_PackageId(tourPackageId);
        return ResponseEntity.ok(itineraries);
    }

    @PostMapping
    public ResponseEntity<String> createItinerary(@RequestBody Itinerary itinerary) {
        Itinerary createdItinerary = itineraryService.createItinerary(itinerary);
        return ResponseEntity.status(HttpStatus.CREATED).body("Itinerary created successfully: " + createdItinerary.getItineraryId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItinerary(@PathVariable Long id, @RequestBody Itinerary itinerary) {
        itinerary.setItineraryId(id);
        Itinerary updatedItinerary = itineraryService.updateItinerary(itinerary);
        return ResponseEntity.ok("Itinerary updated successfully: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItinerary(@PathVariable Long id) {
        itineraryService.deleteItinerary(id);
        return ResponseEntity.ok("Itinerary deleted successfully: " + id);
    }
}