package com.noobs.CampusCart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));
    }

    public User updateProfile(String currentUsername, User updatedUser) {
        User user = getUserByEmail(currentUsername);

        // update editable fields
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            user.setName(updatedUser.getName());
        }
        // if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
        //     user.setUsername(updatedUser.getUsername());
        // }
        if (updatedUser.getLocation() != null && !updatedUser.getLocation().isEmpty()) {
            user.setLocation(updatedUser.getLocation());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }
}
