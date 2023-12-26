package com.example.healthcareapplication.model.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class CreateReactionDTO {
    private Long userId;
    private Long entityId;
    private Long reactionId;
    private Boolean isLike;
    private Boolean isPost;
}
