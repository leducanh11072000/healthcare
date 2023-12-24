package com.example.healthcareapplication.model.dto;

import lombok.Data;

@Data
public class InsertPostDTO {
    private Long userId;
    private String userName;
    private String title;
    private String tags;
    private String content;
}
