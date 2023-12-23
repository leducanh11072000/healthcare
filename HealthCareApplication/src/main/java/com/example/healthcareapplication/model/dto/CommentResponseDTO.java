package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CommentResponseDTO {
    private Long id;
    private Long reactionId;
    private UserInfoDTO userDTO;
    private Long parentid;
    private String content;
    private ReactionDTO reactionDTO;
}
