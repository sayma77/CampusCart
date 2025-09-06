package com.noobs.CampusCart.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.noobs.CampusCart.dto.SigninRequest;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SigninPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/signin")
    public String signin(Model model) {
        return "signin";
    }

    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<?> signinPost(@RequestBody SigninRequest signinData, HttpServletResponse response) {
        Optional<User> opuser = userRepository.findByEmail(signinData.getEmail());

        if (!opuser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Email doesn't exist"));
        }

        User user = opuser.get();
        if (passwordEncoder.matches(signinData.getPassword(), user.getPassword())) {
            Cookie cookie = new Cookie("SESSION_ID", user.getId().toString());
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 10);

            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Signin successful",
                    "redirect", "/"));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("status", "error", "message", "Invalid password"));
    }

}
