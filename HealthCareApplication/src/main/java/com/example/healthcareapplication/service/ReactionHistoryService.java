package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.dto.DataResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReactionHistoryService {

    DataResponse getReactionHistoryByUserId(Long userId);
    DataResponse createHistory(Long userId,Long entityId,Long reactionId,Boolean isLike);
}
