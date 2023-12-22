package com.example.healthcareapplication.model.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String username;
    private String password;
    private String avatarAddress;
    private String email;
}
