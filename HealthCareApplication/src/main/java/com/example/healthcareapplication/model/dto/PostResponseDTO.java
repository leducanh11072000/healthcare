package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
public class PostResponseDTO {
    private Long postId;
    private UserInfoDTO userDTO;
    private LocalDateTime createTime;
    private Long reactionId;
    private String title;
    private String content;
    private ReactionDTO reactionDTO;
    private Map<Long,String> tagName;
    private Long countComment;
    private Long watchTime;
}
