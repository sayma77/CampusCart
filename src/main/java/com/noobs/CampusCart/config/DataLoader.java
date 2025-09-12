package com.noobs.CampusCart.config;

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
                userRepository.saveAll(List.of(admin, u1, u2, u3, u4, u5, u6, u7));
                // ----- Categories -----
                Category c1 = new Category(null, "Electronics", "Devices, gadgets and accessories");
                Category c2 = new Category(null, "Books", "Textbooks, novels, and reference books");
                Category c3 = new Category(null, "Furniture", "Chairs, tables, beds, and other furniture");
                Category c4 = new Category(null, "Stationary", "Pens, notebooks, papers and other office supplies");
                Category c5 = new Category(null, "Sports", "Equipment and gear for various sports activities");
                Category c6 = new Category(null, "Hall Essentials",
                                "Items commonly needed for dorms or halls, like bedding and kitchenware");

                categoryRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6));
                // ----- Products -----
                Product p1 = new Product(null, "Laptop", 500.0, "Good", "/images/laptop.jpg", "sell", "approved",
                                u1.getId(),
                                c1.getId(), null, null);
                Product p2 = new Product(null, "Google pixel 7 pro(12Gb/128GB)", 4000.0, "used for 1 year",
                                "/images/smartphone.jpg", "sell", "approved",
                                u2.getId(), c1.getId(), null, null);
                Product p3 = new Product(null, "Analytic Mechanics", 300.0, "Good", "/images/mechanics.jpg", "rent",
                                "approved", u3.getId(),
                                c2.getId(), null, null);
                Product p4 = new Product(null, "Desk Chair", 40.0, "Good", "/images/chair.jpg", "sell", "approved",
                                u1.getId(), c3.getId(),
                                null, null);
                Product p5 = new Product(null, "Table", 120.0, "Fair", "/images/table.jpg", "sell", "rejected",
                                u2.getId(),
                                c3.getId(), null, null);
                Product p6 = new Product(null, "Calculus Book", 200.0, "Good", "/images/calc_book.jpg", "rent",
                                "pending", u3.getId(),
                                c2.getId(), null, null);
                Product p7 = new Product(null, "Wiresto Earbuds", 500.0, "Good", "/images/wiresto_earbuds.jpg", "sell",
                                "pending", u4.getId(), c1.getId(), null, null);
                Product p8 = new Product(null, "Bed", 700.0, "Fair", "/images/bed.jpg", "sell", "approved", u5.getId(),
                                c3.getId(), null, null);
                Product p9 = new Product(null, "Novel: The Alchemist", 400.0, "Good", "/images/alchemist.jpg", "rent",
                                "approved",
                                u3.getId(), c2.getId(), null, null);
                Product p10 = new Product(null, "Dell E1916HV 18.5\" LED Monitor", 3500.0, "Good",
                                "/images/monitor.jpg", "sell", "approved", u1.getId(), c1.getId(),
                                null, null);
                Product p11 = new Product(null, "Notebook Set", 15.0, "New", "/images/notebook_set.jpg", "sell",
                                "approved", u1.getId(),
                                c4.getId(), null, null);
                Product p12 = new Product(null, "Pen Pack", 30.0, "New", "/images/pen_pack.jpg", "sell", "approved",
                                u2.getId(), c4.getId(),
                                null, null);
                Product p17 = new Product(null, "Highlighter Set", 50.0, "New", "/images/highlighter_set.jpg", "sell",
                                "approved",
                                u5.getId(),
                                c4.getId(), null, null);
                Product p18 = new Product(null, "Sticky Notes", 10.0, "New", "/images/sticky_notes.jpg", "sell",
                                "approved", u1.getId(),
                                c4.getId(),
                                null, null);

                Product p13 = new Product(null, "Football", 25.0, "Good", "/images/football.jpg", "sell", "approved",
                                u4.getId(),
                                c5.getId(), null,
                                null);
                Product p14 = new Product(null, "Badminton", 1600.0, "Like New", "/images/badminton_set.jpg", "sell",
                                "approved",
                                u6.getId(),
                                c5.getId(), null, null);
                Product p19 = new Product(null, "Yoga Mat", 18.0, "New", "/images/yoga_mat.jpg", "sell", "approved",
                                u3.getId(), c5.getId(),
                                null,
                                null);
                Product p20 = new Product(null, "Tennis Racket", 50.0, "Good", "/images/tennis_racket.jpg", "sell",
                                "approved", u4.getId(),
                                c5.getId(), null, null);

                Product p15 = new Product(null, "Bed Sheet", 20.0, "New", "/images/bed_sheet.jpg", "sell", "approved",
                                u6.getId(),
                                c6.getId(), null,
                                null);
                Product p16 = new Product(null, "Desk Lamp", 12.0, "Good", "/images/desk_lamp.jpg", "sell", "approved",
                                u4.getId(),
                                c6.getId(),
                                null, null);
                Product p21 = new Product(null, "Pillow Set", 15.0, "New", "/images/pillow_set.jpg", "sell", "approved",
                                u1.getId(),
                                c6.getId(),
                                null, null);
                Product p22 = new Product(null, "Laundry Basket", 10.0, "New", "/images/laundry_basket.jpg", "sell",
                                "approved", u1.getId(),
                                c6.getId(), null, null);
                Product p23 = new Product(null, "Casio fx-991EX PLUS", 300.0, "Old", "/images/casio_fx_991ex_plus.jpg",
                                "sell", "approved", u2.getId(), c1.getId(), null, null);
                Product p24 = new Product(null, "Electric Kettle", 500.0, "Old", "/images/electric_kettle.jpg", "sell",
                                "approved", u3.getId(), c1.getId(), null, null);
                Product p25 = new Product(null, "TP-link AC-1200", 700.0, "used 4 years", "/images/tplink_ac1200.jpg",
                                "sell", "approved", u4.getId(), c1.getId(), null, null);
                Product p26 = new Product(null, "Wardrobe", 500.0, "used 2 years", "/images/wardrobe.jpg", "sell",
                                "approved", u5.getId(),
                                c3.getId(), null, null);
                Product p27 = new Product(null, "Cyclone Table Fan", 500.0, "used 2 years", "/images/table_fan.jpg",
                                "sell", "approved", u6.getId(),
                                c1.getId(), null, null);
                Product p28 = new Product(null, "Heatmaster elite walton", 1000.0, "used 1 years",
                                "/images/induction_oven.jpg", "sell", "pending", u7.getId(),
                                c1.getId(), null, null);
                Product p29 = new Product(null, "Printer:brother dco t220 (print,scan,copy)", 700.0, "used 4 years",
                                "/images/printer.jpg", "sell", "approved", u7.getId(),
                                c1.getId(), null, null);
                Product p30 = new Product(null, "Pure it Water Filter", 1000.0, "used 4 years", "/images/pureit.jpg",
                                "sell", "pending", u5.getId(),
                                c6.getId(), null, null);
                productRepository.saveAll(
                                List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17,
                                                p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30));
        }
}
