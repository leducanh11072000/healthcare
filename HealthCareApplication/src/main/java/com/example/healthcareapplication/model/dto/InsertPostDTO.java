package com.example.healthcareapplication.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class InsertPostDTO {
    private Long userid;
    private String userName;
    private String title;
    private String tags;
    private String content;
}
