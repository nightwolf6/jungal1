package com.example.demo.Application.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
@ToString(of = "bookingId")
@EqualsAndHashCode(of = "bookingId")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_package_id", nullable = false)
    private TourPackage tourPackage;

    @ManyToOne
    @JoinColumn(name = "itinerary_id", nullable = false)
    private Itinerary itinerary;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "number_of_passengers", nullable = false)
    private int numberOfPassengers;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;
}