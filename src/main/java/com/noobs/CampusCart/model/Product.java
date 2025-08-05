package com.noobs.CampusCart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter          
@AllArgsConstructor           
public class Product {
    private Long id;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
}
