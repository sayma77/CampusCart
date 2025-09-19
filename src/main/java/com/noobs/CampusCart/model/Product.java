package com.noobs.CampusCart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Product entity
@Getter
@Setter
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private String status;

    @Column(name = "image")
    private String image;

    @Column(name = "sell_or_rent")
    private String sellOrRent;

    @Column(name = "validity")
    private String validity;

    // Keep userId for queries
    // @Column(name = "user_id")
    // private Long userId;
    // Keep categoryId for queries (read-only, JPA manages via relation)
    // @Column(name = "category_id", insertable = false, updatable = false)
    // private Long categoryId;
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sold_count")
    private int soldCount;

    // ✅ Category relation (cascade depends on this)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // ✅ Keep user relation for navigation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Legacy constructor for backwards compatibility
    public Product(Long id, String title, String description, int price, String imageUrl) {
        this.id = id;
        this.name = title;
        this.price = (double) price;
        this.image = imageUrl;
        this.status = "Good";
        this.sellOrRent = "sell";
        this.validity = "approved";
        this.quantity = 1;
        this.soldCount = 0;
    }

    public Product(String name, Double price, String status, String image,
            String sellOrRent, String validity, User user, Category category,
            int quantity, int soldCount) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.image = image;
        this.sellOrRent = sellOrRent;
        this.validity = validity;
        this.user = user;
        this.category = category;
        this.quantity = quantity;
        this.soldCount = soldCount;
    }

}
