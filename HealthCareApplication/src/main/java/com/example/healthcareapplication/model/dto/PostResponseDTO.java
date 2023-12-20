package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
@Data
@SuperBuilder
public class PostResponseDTO {
    private Long postId;
    private UserInfoDTO userDTO;
    private LocalDateTime createTime;
    private String title;
    private String content;
    private ReactionDTO reactionDTO;
    private List<String> tagName;
}
