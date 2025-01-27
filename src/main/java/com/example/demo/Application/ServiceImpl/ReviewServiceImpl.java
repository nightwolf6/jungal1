package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.Review;
import com.example.demo.Application.Repository.ReviewRepository;
import com.example.demo.Application.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> getReviewsByTourPackageId(Long tourPackageId) {
        return reviewRepository.findByTourPackage_PackageId(tourPackageId);
    }

    @Override
    public Review saveReview(Review review) {
        validateRating(review.getRating());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Review createReview(Review review) {
        validateRating(review.getRating());
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        validateRating(review.getRating());
        return reviewRepository.save(review);
    }

    private void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
