package com.noobs.CampusCart.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
}