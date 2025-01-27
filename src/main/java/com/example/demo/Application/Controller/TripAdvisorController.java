package com.example.demo.Application.Controller;

import com.example.demo.Application.Service.TripAdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tripadvisor")
public class TripAdvisorController {

    @Autowired
    private TripAdvisorService tripAdvisorService;

    @GetMapping("/reviews/{locationId}")
    public String getReviews(@PathVariable String locationId) {
        return tripAdvisorService.getReviews(locationId);
    }
}
