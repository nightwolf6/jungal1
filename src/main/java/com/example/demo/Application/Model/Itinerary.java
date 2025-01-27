package com.example.demo.Application.Model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itineraries")
@ToString(of = "itineraryId")
@EqualsAndHashCode(of = "itineraryId")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_id")
    private Long itineraryId;

    @ManyToOne
    @JoinColumn(name = "tour_package_id", nullable = false)
    private TourPackage tourPackage;

    @Column(name = "day_number", nullable = false)
    private int dayNumber;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_per_passenger", nullable = false)
    private double pricePerPassenger;

    // Getters and setters

    public Long getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Long itineraryId) {
        this.itineraryId = itineraryId;
    }

}