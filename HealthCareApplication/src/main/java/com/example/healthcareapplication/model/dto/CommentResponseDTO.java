package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class CommentResponseDTO {
    private Long id;
    private Long reactionId;
    private UserInfoDTO userDTO;
    private Long parentId;
    private String content;
    private ReactionDTO reactionDTO;
    private LocalDateTime createTime;
    private Long postId;
}
