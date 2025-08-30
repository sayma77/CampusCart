package com.noobs.CampusCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // All products with user and category
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user")
    List<Product> findAllWithUserAndCategory();

    // By category
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE p.category.name = :categoryId")
    List<Product> findByCategoryIdWithUserAndCategory(@Param("categoryId") String categoryId);

    // By type (sell or rent)
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE LOWER(p.sellOrRent) = LOWER(:type)")
    List<Product> findByTypeWithUserAndCategory(@Param("type") String type);

    // By category AND type
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE p.category.name = :categoryId AND LOWER(p.sellOrRent) = LOWER(:type)")
    List<Product> findByCategoryAndTypeWithUserAndCategory(@Param("categoryId") String categoryId, @Param("type") String type);

    // Search by name (case-insensitive)
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByNameContainingWithUserAndCategory(@Param("keyword") String keyword);

    // Find products posted by a specific user with a specific type (sell or rent)
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE p.user = :user AND LOWER(p.sellOrRent) = LOWER(:type)")
    List<Product> findByUserAndSellOrRent(@Param("user") User user, @Param("type") String type);
    
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE p.user = :user")
    List<Product> findByUser(@Param("user") User user);
}
