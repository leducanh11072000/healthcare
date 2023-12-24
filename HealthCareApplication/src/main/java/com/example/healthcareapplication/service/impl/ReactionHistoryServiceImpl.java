package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.Reaction;
import com.example.healthcareapplication.model.ReactionHistory;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.repository.ReactionHistoryRepository;
import com.example.healthcareapplication.repository.ReactionRepository;
import com.example.healthcareapplication.service.ReactionHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ReactionHistoryServiceImpl  implements ReactionHistoryService {
    @Autowired
    private final ReactionHistoryRepository reactionHistoryRepository;
    @Autowired
    private final ReactionRepository reactionRepository;
    @Override
    public DataResponse getReactionHistoryByUserId(Long userId) {
        try {
            List<ReactionHistory> reactionHistories = reactionHistoryRepository.findAllByUserIdAndStatus(userId,Common.ACTIVE_STATUS);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, reactionHistories);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
    }

    @Override
    public DataResponse createHistory(Long userId, Long entityId,Long reactionId, Boolean isLike, Boolean isPost) {
        try {
            List<ReactionHistory> reactionHistories = reactionHistoryRepository.findAllByUserIdAndStatus(userId,Common.ACTIVE_STATUS);
            ReactionHistory reactionHistory = reactionHistories.stream().filter(e -> e.getEntityReactionId().equals(entityId)).findFirst().orElse(null);
            Reaction reaction = reactionRepository.findById(reactionId).orElse(null);
            if ( reactionHistory != null) {
                if (reactionHistory.getIsLike() == isLike) {
                    reactionHistory.setStatus(0L);
                    if (isLike) {
                        reaction.setLike(reaction.getLike()-1);
                    } else {
                        reaction.setDislike(reaction.getDislike()-1);
                    }
                    reactionRepository.save(reaction);
                    return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, reactionHistoryRepository.save(reactionHistory));
                }
                reactionHistory.setIsLike(isLike);
                if (isLike) {
                    reaction.setLike(reaction.getLike()+1);
                    reaction.setDislike(reaction.getDislike()-1);
                } else {
                    reaction.setLike(reaction.getDislike()-1);
                    reaction.setDislike(reaction.getDislike()+1);
                }
                reactionRepository.save(reaction);
                return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,  reactionHistoryRepository.save(reactionHistory));
            }
            if (isLike) {
                reaction.setLike(reaction.getLike()+1);
            } else {
                reaction.setDislike(reaction.getDislike()-1);
            }
            reactionRepository.save(reaction);
            ReactionHistory newHistory = ReactionHistory.builder()
                    .status(Common.ACTIVE_STATUS)
                    .entityReactionId(entityId)
                    .userId(userId)
                    .reactionId(reaction.getId())
                    .isLike(isLike)
                    .isPost(isPost)
                    .build();
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, reactionHistoryRepository.save(newHistory));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
    }
}
