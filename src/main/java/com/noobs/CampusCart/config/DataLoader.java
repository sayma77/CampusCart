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
        User u7 = new User(null,"Sadia","u2204006@student.cuet.ac.bd","Sadia","01586238018",passwordEncoder.encode("project"),"USER","Taposhi Rabeya Hall");
        userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));

        // ----- Categories -----
        Category c1 = new Category(null, "Electronics", "Devices, gadgets and accessories");
        Category c2 = new Category(null, "Books", "Textbooks, novels, and reference books");
        Category c3 = new Category(null, "Furniture", "Chairs, tables, beds, and other furniture");
        Category c4 = new Category(null, "Stationary", "Pens, notebooks, papers and other office supplies");
        Category c5 = new Category(null, "Sports", "Equipment and gear for various sports activities");
        Category c6 = new Category(null, "Hall Essentials", "Items commonly needed for dorms or halls, like bedding and kitchenware");

        categoryRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6));

        // ----- Products -----
        Product p1 = new Product(null, "Laptop", 500.0, "Good", "/images/laptop.jpg", "sell", u1.getId(), c1.getId(), null, null);
        Product p2 = new Product(null, "Smartphone", 300.0, "Fair", "/images/smartphone.jpg", "sell", u2.getId(), c1.getId(), null, null);
        Product p3 = new Product(null, "Data Structures Book", 25.0, "Good", "ds_book.jpg", "rent", u3.getId(), c2.getId(), null, null);
        Product p4 = new Product(null, "Desk Chair", 40.0, "Good", "chair.jpg", "sell", u1.getId(), c3.getId(), null, null);
        Product p5 = new Product(null, "Dining Table", 120.0, "Fair", "table.jpg", "sell", u2.getId(), c3.getId(), null, null);
        Product p6 = new Product(null, "Calculus Book", 20.0, "Good", "calc_book.jpg", "rent", u3.getId(), c2.getId(), null, null);
        Product p7 = new Product(null, "Headphones", 60.0, "Good", "headphones.jpg", "sell", u4.getId(), c1.getId(), null, null);
        Product p8 = new Product(null, "Bed Frame", 150.0, "Fair", "bed.jpg", "sell", u5.getId(), c3.getId(), null, null);
        Product p9 = new Product(null, "Novel: The Alchemist", 15.0, "Good", "alchemist.jpg", "rent", u3.getId(), c2.getId(), null, null);
        Product p10 = new Product(null, "Monitor", 200.0, "Good", "monitor.jpg", "sell", u1.getId(), c1.getId(), null, null);

        Product p11 = new Product(null, "Notebook Set", 15.0, "New", "notebook_set.jpg", "sell", u1.getId(), c4.getId(), null, null);
        Product p12 = new Product(null, "Pen Pack", 5.0, "New", "pen_pack.jpg", "sell", u2.getId(), c4.getId(), null, null);
        Product p17 = new Product(null, "Highlighter Set", 7.0, "New", "highlighter_set.jpg", "sell", u5.getId(), c4.getId(), null, null);
        Product p18 = new Product(null, "Sticky Notes", 3.0, "New", "sticky_notes.jpg", "sell", u1.getId(), c4.getId(), null, null);

        Product p13 = new Product(null, "Football", 25.0, "Good", "football.jpg", "sell", u4.getId(), c5.getId(), null, null);
        Product p14 = new Product(null, "Badminton Set", 40.0, "Like New", "badminton_set.jpg", "sell", u6.getId(), c5.getId(), null, null);
        Product p19 = new Product(null, "Yoga Mat", 18.0, "New", "yoga_mat.jpg", "sell", u3.getId(), c5.getId(), null, null);
        Product p20 = new Product(null, "Tennis Racket", 50.0, "Good", "tennis_racket.jpg", "sell", u4.getId(), c5.getId(), null, null);

        Product p15 = new Product(null, "Bed Sheet", 20.0, "New", "bed_sheet.jpg", "sell", u6.getId(), c6.getId(), null, null);
        Product p16 = new Product(null, "Desk Lamp", 12.0, "Good", "desk_lamp.jpg", "sell", u4.getId(), c6.getId(), null, null);
        Product p21 = new Product(null, "Pillow Set", 15.0, "New", "pillow_set.jpg", "sell", u1.getId(), c6.getId(), null, null);
        Product p22 = new Product(null, "Laundry Basket", 10.0, "New", "laundry_basket.jpg", "sell", u1.getId(), c6.getId(), null, null);

        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22));
    }
}
