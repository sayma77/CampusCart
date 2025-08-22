package com.noobs.CampusCart.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.noobs.CampusCart.model.Category;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CategoryRepository;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // ----- Users -----
        User u1 = new User(null, "alice123", "alice@cuet.ac.bd", "Alice Akter", "01710000001", passwordEncoder.encode("password1"), "USER", "qk_hall");
        User u2 = new User(null, "bob456", "bob@cuet.ac.bd", "Bob Karim", "01710000002", passwordEncoder.encode("password2"), "USER", "north_hall");
        User u3 = new User(null, "carol789", "carol@cuet.ac.bd", "Carol Rahman", "01710000003", passwordEncoder.encode("password3"), "USER", "south_hall");
        User u4 = new User(null, "david321", "david@cuet.ac.bd", "David Hossain", "01710000004", passwordEncoder.encode("password4"), "USER", "bb_hall");
        User u5 = new User(null, "emma654", "emma@cuet.ac.bd", "Emma Chowdhury", "01710000005", passwordEncoder.encode("password5"), "USER", "rabeya_hall");
        User u6 = new User(null, "asdf", "asdf@mail.com", "asdf asdf", "01710000006", passwordEncoder.encode("asdf"), "USER", "shamshen_nahar_hall");

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));

        // ----- Categories -----
        Category c1 = new Category(null, "Electronics", "Devices, gadgets and accessories");
        Category c2 = new Category(null, "Books", "Textbooks, novels, and reference books");
        Category c3 = new Category(null, "Furniture", "Chairs, tables, beds, and other furniture");

        categoryRepository.saveAll(List.of(c1, c2, c3));

        // ----- Products -----
        Product p1 = new Product(null, "Laptop", 500.0, "Good", "laptop.jpg", "sell", u1.getId(), c1.getId(), null, null);
        Product p2 = new Product(null, "Smartphone", 300.0, "Fair", "smartphone.jpg", "sell", u2.getId(), c1.getId(), null, null);
        Product p3 = new Product(null, "Data Structures Book", 25.0, "Good", "ds_book.jpg", "rent", u3.getId(), c2.getId(), null, null);
        Product p4 = new Product(null, "Desk Chair", 40.0, "Good", "chair.jpg", "sell", u1.getId(), c3.getId(), null, null);
        Product p5 = new Product(null, "Dining Table", 120.0, "Fair", "table.jpg", "sell", u2.getId(), c3.getId(), null, null);
        Product p6 = new Product(null, "Calculus Book", 20.0, "Good", "calc_book.jpg", "rent", u3.getId(), c2.getId(), null, null);
        Product p7 = new Product(null, "Headphones", 60.0, "Good", "headphones.jpg", "sell", u4.getId(), c1.getId(), null, null);
        Product p8 = new Product(null, "Bed Frame", 150.0, "Fair", "bed.jpg", "sell", u5.getId(), c3.getId(), null, null);
        Product p9 = new Product(null, "Novel: The Alchemist", 15.0, "Good", "alchemist.jpg", "rent", u3.getId(), c2.getId(), null, null);
        Product p10 = new Product(null, "Monitor", 200.0, "Good", "monitor.jpg", "sell", u1.getId(), c1.getId(), null, null);

        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }
}
