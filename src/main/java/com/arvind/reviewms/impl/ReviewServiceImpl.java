package com.arvind.reviewms.impl;

import com.arvind.reviewms.Review;
import com.arvind.reviewms.ReviewRepository;
import com.arvind.reviewms.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Override
    public List<Review> getReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId,Review review) {
        if(companyId!=null && review!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateReview(Long id, Review reviewToUpdate) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isPresent()){
            Review review = reviewOptional.get();
            reviewToUpdate.setId(id);
            reviewToUpdate.setCompanyId(review.getCompanyId());
            reviewRepository.save(reviewToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isPresent()){
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
