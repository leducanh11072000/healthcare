package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
public class UpdatePostDTO {
    private Long userId;
    private Long postId;
    private String newContent;
    private String newTitle;
}
