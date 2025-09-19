package com.noobs.CampusCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.noobs.CampusCart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // --- Existing approved queries ---
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE LOWER(p.validity) = 'approved'")
    List<Product> findAllApproved();

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user "
            + "WHERE p.category.name = :category AND LOWER(p.validity) = 'approved'")
    List<Product> findByCategoryNameAndApproved(@Param("category") String category);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user "
            + "WHERE LOWER(p.sellOrRent) = LOWER(:type) AND LOWER(p.validity) = 'approved'")
    List<Product> findByTypeAndApproved(@Param("type") String type);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user "
            + "WHERE p.category.name = :category AND LOWER(p.sellOrRent) = LOWER(:type) "
            + "AND LOWER(p.validity) = 'approved'")
    List<Product> findByCategoryAndTypeAndApproved(@Param("category") String category,
            @Param("type") String type);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user "
            + "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND LOWER(p.validity) = 'approved'")
    List<Product> findByNameContainingAndApproved(@Param("keyword") String keyword);

    // --- New methods for admin (all products, any validity) ---
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user")
    List<Product> findAllWithUserAndCategory(); // admin can see everything

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user WHERE LOWER(p.validity) = LOWER(:validity)")
    List<Product> findByValidity(@Param("validity") String validity);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user "
            + "WHERE p.category.id = :categoryId AND LOWER(p.validity) = LOWER(:validity)")
    List<Product> findByCategoryAndValidity(@Param("categoryId") Long categoryId,
            @Param("validity") String validity);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.user "
            + "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByNameContainingWithUserAndCategory(@Param("keyword") String keyword);
}
