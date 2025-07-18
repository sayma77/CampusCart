package com.noobs.CampusCart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noobs.CampusCart.dto.AuthResponse;
import com.noobs.CampusCart.dto.SigninRequest;
import com.noobs.CampusCart.dto.SignupRequest;
import com.noobs.CampusCart.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest req) {
        return ResponseEntity.ok(authService.signup(req));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody SigninRequest req) {
        return ResponseEntity.ok(authService.signin(req));
    }
}

