package com.arvind.reviewms;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews(Long companyId);
    boolean createReview(Long companyId,Review review);
    Review getReview(Long id);
    boolean updateReview(Long id, Review review);
    boolean deleteReview(Long id);

}
