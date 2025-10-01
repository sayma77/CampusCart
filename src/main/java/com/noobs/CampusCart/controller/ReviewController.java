package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noobs.CampusCart.model.Review;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.ReviewRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    // Save a review
    @PostMapping("/reviews")
    public String addReview(
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment,
            Principal principal) {

        // Find the logged-in user
        User user = userRepository.findByEmail(principal.getName())
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
