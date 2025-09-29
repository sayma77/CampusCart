package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noobs.CampusCart.model.Review;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.ReviewRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Controller
public class ReviewController {
    @Autowired 
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    // Save a review
    @PostMapping("/reviews")
    public String addReview(
        @RequestParam("rating") int rating,
        @RequestParam("comment") String comment,
        @RequestParam(value = "productId", required = false) Long productId,
        @RequestParam(value = "from", required = false) String from,
        Principal principal) {

    User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    Review.ReviewBuilder builder = Review.builder()
            .rating(rating)
            .comment(comment)
            .reviewDate(LocalDateTime.now())
            .user(user);

    if (productId != null) {
        builder.product(productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    reviewRepository.save(builder.build());

    // Redirect depending on where review came from
    if ("home".equals(from)) {
        return "redirect:/#home-reviews"; // back to homepage
    } else if (productId != null) {
        return "redirect:/marketplace#product-" + productId;
    } else {
        return "redirect:/marketplace";
    }
}

}
