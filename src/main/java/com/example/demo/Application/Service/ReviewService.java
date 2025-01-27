package com.example.demo.Application.Service;

import com.example.demo.Application.Model.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getAllReviews();

    List<Review> getReviewsByUserId(Long userId);

    Optional<Review> getReviewById(Long id);

    List<Review> getReviewsByTourPackageId(Long tourPackageId);

    Review saveReview(Review review);

    void deleteReview(Long id);

    Review createReview(Review review);

    Review updateReview(Review review);

}
