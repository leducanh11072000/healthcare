package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
public class DeletePostDTO {
    private Long userId;
    private Long postId;
}
