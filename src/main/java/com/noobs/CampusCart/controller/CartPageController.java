package com.noobs.CampusCart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    @GetMapping("/cart")
    public String cartPage(Model model) {
        List<Object> cartItems = List.of(
            new Object() {
                public String type = "BUY";
                public String condition = "Good Condition";
                public String title = "Casio fx-991ES PLUS Calculator";
                public String author = "Scientific Calculator";
                public String seller = "Rahul Ahmed (EEE-19)";
                public int price = 1800;
                public int quantity = 1;
                public String image = "https://www.casio-intl.com/asia/en/calc/products/images/fx-991ESPLUS-2.jpg";
            },
            new Object() {
                public String type = "RENT";
                public String condition = "Like New";
                public String title = "Steel T-Scale (120 cm)";
                public String author = "For Engineering Drawing";
                public String seller = "Fatima Khan (ARCH-21)";
                public int price = 150;
                public int quantity = 1;
                public String image = "https://waykenrm.com/wp-content/uploads/2021/12/engineering-drawing.jpg";
            },
            new Object() {
                public String type = "BUY";
                public String condition = "Brand New";
                public String title = "Set Square Set (30°/60°/90°)";
                public String author = "Transparent Plastic";
                public String seller = "Arif Hassan (CSE-20)";
                public int price = 80;
                public int quantity = 1;
                public String image = "https://www.cavalierart.com.au/wp-content/uploads/2009/05/setsquare_kent12.jpg";
            },
            new Object() {
                public String type = "BUY";
                public String condition = "Good Condition";
                public String title = "Introduction to Algorithms (CLRS)";
                public String author = "by Thomas H. Cormen";
                public String seller = "Mitu Sultana (CSE-19)";
                public int price = 1200;
                public int quantity = 1;
                public String image = "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1387741681i/108986.jpg";
            }
        );

        int subtotal = cartItems.stream()
            .mapToInt(i -> {
                try {
                    return (int) i.getClass().getField("price").get(i) *
                           (int) i.getClass().getField("quantity").get(i);
                } catch (Exception e) {
                    return 0;
                }
            })
            .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("currency", "৳"); // Taka symbol

        return "cart";
    }
}




