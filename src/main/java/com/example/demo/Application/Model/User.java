package com.example.demo.Application.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private String email;

    @Column(name = "identity_or_passport", length = 15, unique = true, nullable = false)
    private String identityOrPassport;

    @Column(name = "photo", nullable = true)
    private byte[] photo;

    @Column(name = "verification_token", unique = true)
    private String verificationToken;

    @Column(name = "verified", nullable = false)
    private boolean verified = false;

    @Column(name = "image_identifier", nullable = true)
    private String imageIdentifier;

}
