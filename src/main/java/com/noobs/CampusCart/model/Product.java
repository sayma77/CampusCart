package com.noobs.CampusCart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter          
@AllArgsConstructor 
@NoArgsConstructor          
public class Product {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String imageUrl;

   
}
