package com.example.demo.Application.Repository;

import com.example.demo.Application.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTourPackage_PackageId(Long tourPackageId);
    List<Review> findByUserId(Long userId);
}