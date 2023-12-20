package com.example.healthcareapplication.model.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long commentId;
    private Long postId;
    private Long userId;
    private Long parentId;
    private String content;
}
