package com.example.demo.Application.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@ToString(of = "imageId")
@EqualsAndHashCode(of = "imageId")

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filepath;
}
