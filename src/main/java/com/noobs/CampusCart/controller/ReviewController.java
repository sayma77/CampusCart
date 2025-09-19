package com.noobs.CampusCart.controller;

import com.noobs.CampusCart.model.Review;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.ReviewRepository;
import com.noobs.CampusCart.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    // Save a review
    @PostMapping("/reviews")
    public String addReview(@RequestParam int rating,
                            @RequestParam String comment,
                            Principal principal) {

        // Find the logged-in user
        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = Review.builder()
                .rating(rating)
                .comment(comment)
                .reviewDate(LocalDateTime.now())
                .user(user)
                .build();

        reviewRepository.save(review);

        // Redirect back to homepage reviews section
        return "redirect:/#reviews";
    }

    // (Optional) Separate page to view all reviews
    @GetMapping("/reviews")
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews"; // create reviews.html if you want
    }
}
