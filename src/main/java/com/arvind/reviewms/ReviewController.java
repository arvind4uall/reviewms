package com.arvind.reviewms;

import com.arvind.reviewms.impl.ReviewServiceImpl;
import com.arvind.reviewms.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewServiceImpl reviewServiceImpl;
    @Autowired
    private ReviewMessageProducer reviewMessageProducer;
    @GetMapping
    private ResponseEntity<List<Review>> getReviews(@RequestParam Long companyId){
        List<Review> reviews = reviewServiceImpl.getReviews(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<String> createReview(@RequestParam Long companyId,@RequestBody Review review){
        boolean isCreated = reviewServiceImpl.createReview(companyId,review);
        if(isCreated){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review created successfully!!!",HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Error occured while creating reivew!!",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/{reviewId}")
    private ResponseEntity<Review> getReview(@PathVariable Long reviewId){
       Review review = reviewServiceImpl.getReview(reviewId);
       if(review!=null)
           return new ResponseEntity<>(review,HttpStatus.OK);
       return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{reviewId}")
    private ResponseEntity<String> updateReview(@PathVariable Long reviewId,@RequestBody Review review){
        boolean isUpdated = reviewServiceImpl.updateReview(reviewId,review);
        if(isUpdated)
            return new ResponseEntity<>("Review updated successfully!!",HttpStatus.OK);
        return new ResponseEntity<>("Review not found!!",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{reviewId}")
    private ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isDeleted = reviewServiceImpl.deleteReview(reviewId);
        if(isDeleted)
            return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
        return new ResponseEntity<>("Review doesn't present !!!",HttpStatus.NOT_FOUND);
    }
}
