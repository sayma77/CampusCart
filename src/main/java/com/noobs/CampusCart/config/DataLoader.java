package com.noobs.CampusCart.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.noobs.CampusCart.model.Category;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CategoryRepository;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Configuration
@Profile("dev")
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
        if (userRepository.count() != 0 || categoryRepository.count() != 0 || productRepository.count() != 0) {
            return;
        }
        User admin = new User(null, "admin", "admin@cuet.ac.bd", "very very admin", "01710000000",
                passwordEncoder.encode("admin"), "ADMIN", "");

        User u1 = new User(null, "alice123", "alice@cuet.ac.bd", "Alice Akter", "01710000001",
                passwordEncoder.encode("password1"), "USER", "qk_hall");
        User u2 = new User(null, "bob456", "bob@cuet.ac.bd", "Bob Karim", "01710000002",
                passwordEncoder.encode("password2"), "USER", "north_hall");
        User u3 = new User(null, "carol789", "carol@cuet.ac.bd", "Carol Rahman", "01710000003",
                passwordEncoder.encode("password3"), "USER", "south_hall");
        User u4 = new User(null, "david321", "david@cuet.ac.bd", "David Hossain", "01710000004",
                passwordEncoder.encode("password4"), "USER", "bb_hall");
        User u5 = new User(null, "emma654", "emma@cuet.ac.bd", "Emma Chowdhury", "01710000005",
                passwordEncoder.encode("password5"), "USER", "rabeya_hall");
        User u6 = new User(null, "asdf", "asdf@mail.com", "asdf asdf", "01710000006",
                passwordEncoder.encode("asdf"),
                "USER", "shamshen_nahar_hall");
        User u7 = new User(null, "Sadia", "u2204006@student.cuet.ac.bd", "Sadia", "01586238018",
                passwordEncoder.encode("project"), "USER", "Taposhi Rabeya Hall");
        User u8 = new User(null, "sayma", "u2204008@student.cuet.ac.bd", "Sayma Akter", "01376248117",
                passwordEncoder.encode("hello"), "USER", "");
        userRepository.saveAll(List.of(admin, u1, u2, u3, u4, u5, u6, u7, u8));
        // ----- Categories -----
        Category c1 = new Category(null, "Electronics", "Devices, gadgets and accessories", new ArrayList<>());
        Category c2 = new Category(null, "Books", "Textbooks, novels, and reference books", new ArrayList<>());
        Category c3 = new Category(null, "Furniture", "Chairs, tables, beds, and other furniture",
                new ArrayList<>());
        Category c4 = new Category(null, "Stationary", "Pens, notebooks, papers and other office supplies",
                new ArrayList<>());
        Category c5 = new Category(null, "Sports", "Equipment and gear for various sports activities",
                new ArrayList<>());
        Category c6 = new Category(null, "Hall Essentials",
                "Items commonly needed for dorms or halls, like bedding and kitchenware",
                new ArrayList<>());

        categoryRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6));
        // ----- Products -----
        // Long id, String title, String description, int price, String imageUrl ,
        // Category category , User user
        Product p1 = new Product("Laptop", 500.0, "Good", "/images/laptop.jpg", "sell", "approved", u1, c1, 10,
                2);
        Product p2 = new Product("Google pixel 7 pro(12Gb/128GB)", 4000.0, "used for 1 year",
                "/images/smartphone.jpg", "sell", "approved", u2, c1, 5, 1);
        Product p3 = new Product("Analytic Mechanics", 300.0, "Good", "/images/mechanics.jpg", "rent",
                "approved", u3, c2, 3, 0);
        Product p4 = new Product("Desk Chair", 40.0, "Good", "/images/chair.jpg", "sell", "approved", u1, c3,
                15, 5);
        Product p5 = new Product("Table", 120.0, "Fair", "/images/table.jpg", "sell", "rejected", u2, c3, 7, 3);
        Product p6 = new Product("Calculus Book", 200.0, "Good", "/images/calc_book.jpg", "rent", "pending", u3,
                c2, 4, 0);
        Product p7 = new Product("Wiresto Earbuds", 500.0, "Good", "/images/wiresto_earbuds.jpg", "sell",
                "pending", u4, c1, 8, 2);
        Product p8 = new Product("Bed", 700.0, "Fair", "/images/bed.jpg", "sell", "approved", u5, c3, 6, 1);
        Product p9 = new Product("Novel: The Alchemist", 400.0, "Good", "/images/alchemist.jpg", "rent",
                "approved", u3, c2, 10, 3);
        Product p10 = new Product("Dell E1916HV 18.5\" LED Monitor", 3500.0, "Good", "/images/monitor.jpg",
                "sell", "approved", u1, c1, 4, 1);
        Product p11 = new Product("Notebook Set", 15.0, "New", "/images/notebook_set.jpg", "sell", "approved",
                u1, c4, 20, 10);
        Product p12 = new Product("Pen Pack", 30.0, "New", "/images/pen_pack.jpg", "sell", "approved", u2, c4,
                30, 5);
        Product p13 = new Product("Football", 25.0, "Good", "/images/football.jpg", "sell", "approved", u4, c5,
                15, 6);
        Product p14 = new Product("Badminton", 1600.0, "Like New", "/images/badminton_set.jpg", "sell",
                "approved", u6, c5, 8, 2);
        Product p15 = new Product("Bed Sheet", 20.0, "New", "/images/bed_sheet.jpg", "sell", "approved", u6, c6,
                25, 8);
        Product p16 = new Product("Desk Lamp", 12.0, "Good", "/images/desk_lamp.jpg", "sell", "approved", u4,
                c6, 18, 5);
        Product p17 = new Product("Highlighter Set", 50.0, "New", "/images/highlighter_set.jpg", "sell",
                "approved", u5, c4, 25, 7);
        Product p18 = new Product("Sticky Notes", 10.0, "New", "/images/sticky_notes.jpg", "sell", "approved",
                u1, c4, 40, 12);
        Product p19 = new Product("Yoga Mat", 18.0, "New", "/images/yoga_mat.jpg", "sell", "approved", u3, c5,
                12, 3);
        Product p20 = new Product("Tennis Racket", 50.0, "Good", "/images/tennis_racket.jpg", "sell",
                "approved", u4, c5, 10, 4);
        Product p21 = new Product("Pillow Set", 15.0, "New", "/images/pillow_set.jpg", "sell", "approved", u1,
                c6, 20, 10);
        Product p22 = new Product("Laundry Basket", 10.0, "New", "/images/laundry_basket.jpg", "sell",
                "approved", u1, c6, 30, 12);
        Product p23 = new Product("Casio fx-991EX PLUS", 300.0, "Old", "/images/casio_fx_991ex_plus.jpg",
                "sell", "approved", u2, c1, 5, 1);
        Product p24 = new Product("Electric Kettle", 500.0, "Old", "/images/electric_kettle.jpg", "sell",
                "approved", u3, c1, 6, 2);
        Product p25 = new Product("TP-link AC-1200", 700.0, "used 4 years", "/images/tplink_ac1200.jpg", "sell",
                "approved", u4, c1, 4, 1);
        Product p26 = new Product("Wardrobe", 500.0, "used 2 years", "/images/wardrobe.jpg", "sell", "approved",
                u5, c3, 3, 1);
        Product p27 = new Product("Cyclone Table Fan", 500.0, "used 2 years", "/images/table_fan.jpg", "sell",
                "approved", u6, c1, 7, 3);
        Product p28 = new Product("Heatmaster elite walton", 1000.0, "used 1 years",
                "/images/induction_oven.jpg", "sell", "pending", u7, c1, 2, 0);
        Product p29 = new Product("Printer:brother dco t220 (print,scan,copy)", 700.0, "used 4 years",
                "/images/printer.jpg", "sell", "approved", u7, c1, 3, 1);
        Product p30 = new Product("Pure it Water Filter", 1000.0, "used 4 years", "/images/pureit.jpg", "sell",
                "pending", u5, c6, 2, 0);

        productRepository.saveAll(
                List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17,
                        p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30));
    }
}
