package com.example.demo.Application.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checkins")
@ToString(of = "checkinId")
@EqualsAndHashCode(of = "checkinId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Checkin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkin_id")
    private Long checkinId;

    @Column(name = "accepted_facial_recognition", nullable = false)
    private Boolean acceptedFacialRecognition;

    @Column(name = "facial_recognition_approved", nullable = true)
    private Boolean facialRecognitionApproved;

    @Column(name = "checkin_completed", nullable = false)
    private Boolean checkinCompleted;

    @Column(name = "new_photo", nullable = true)
    private byte[] newPhoto;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "altitude", nullable = true)
    private Double altitude;

    @Column(name = "checkin_date", nullable = false)
    private LocalDate checkinDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "itinerary_id", nullable = false)
    private Itinerary itinerary;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}
