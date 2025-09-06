package com.noobs.CampusCart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.noobs.CampusCart.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAdminFlag(Model model, Principal principal) {
        boolean isAdmin = false;
        if (principal != null) {
            isAdmin = userService.isAdmin(principal.getName());
        }
        model.addAttribute("isAdmin", isAdmin);
    }
}
