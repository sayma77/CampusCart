package com.noobs.CampusCart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.noobs.CampusCart.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId); // for marketplace product reviews
}
