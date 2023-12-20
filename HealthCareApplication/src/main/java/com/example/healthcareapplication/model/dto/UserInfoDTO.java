package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserInfoDTO {
    private Long id;
    private String username;
    private String avatarAddress;
    private String tagName;
    private String email;
}
