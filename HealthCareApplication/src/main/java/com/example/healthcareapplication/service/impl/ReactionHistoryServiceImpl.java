package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.Reaction;
import com.example.healthcareapplication.model.ReactionHistory;
import com.example.healthcareapplication.model.dto.*;
import com.example.healthcareapplication.repository.ReactionHistoryRepository;
import com.example.healthcareapplication.repository.ReactionRepository;
import com.example.healthcareapplication.service.CommentService;
import com.example.healthcareapplication.service.PostService;
import com.example.healthcareapplication.service.ReactionHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ReactionHistoryServiceImpl  implements ReactionHistoryService {
    @Autowired
    private final ReactionHistoryRepository reactionHistoryRepository;
    @Autowired
    private final ReactionRepository reactionRepository;
    @Autowired
    @Lazy
    private final PostService postService;
    @Autowired
    @Lazy
    private final CommentService commentService;
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
    public DataResponse createHistory(CreateReactionDTO createReactionDTO) {
        try {
            List<ReactionHistory> reactionHistories = reactionHistoryRepository.findAllByUserIdAndStatus(createReactionDTO.getUserId(),Common.ACTIVE_STATUS);
            ReactionHistory reactionHistory = reactionHistories.stream().filter(e -> e.getEntityReactionId().equals(createReactionDTO.getEntityId()) && e.getIsPost() == createReactionDTO.getIsPost()).findFirst().orElse(null);
            Reaction reaction = reactionRepository.findById(createReactionDTO.getReactionId()).orElse(null);
            if ( reactionHistory != null) {
                if (reactionHistory.getIsLike() == createReactionDTO.getIsLike()) {
                    reactionHistory.setStatus(0L);
                    if (createReactionDTO.getIsLike()) {
                        reaction.setLike(reaction.getLike()-1);
                    } else {
                        reaction.setDislike(reaction.getDislike()-1);
                    }
                    reactionRepository.save(reaction);
                    return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, reactionHistoryRepository.save(reactionHistory));
                }
                reactionHistory.setIsLike(createReactionDTO.getIsLike());
                if (createReactionDTO.getIsLike()) {
                    reaction.setLike(reaction.getLike()+1);
                    reaction.setDislike(reaction.getDislike()-1);
                } else {
                    reaction.setLike(reaction.getLike()-1);
                    reaction.setDislike(reaction.getDislike()+1);
                }
                reactionRepository.save(reaction);
                return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,  reactionHistoryRepository.save(reactionHistory));
            }
            if (createReactionDTO.getIsLike()) {
                reaction.setLike(reaction.getLike()+1);
            } else {
                reaction.setDislike(reaction.getDislike()-1);
            }
            reactionRepository.save(reaction);
            ReactionHistory newHistory = ReactionHistory.builder()
                    .status(Common.ACTIVE_STATUS)
                    .entityReactionId(createReactionDTO.getEntityId())
                    .userId(createReactionDTO.getUserId())
                    .reactionId(reaction.getId())
                    .isLike(createReactionDTO.getIsLike())
                    .isPost(createReactionDTO.getIsPost())
                    .build();
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, reactionHistoryRepository.save(newHistory));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
    }
    @Override
    public DataResponse count(Long userId) {
        List<PostResponseDTO> postResponseDTOS = (List<PostResponseDTO>) postService.getAllByUserId(userId,0L,10000L).getData();
        List<CommentResponseDTO> commentResponseDTOS = commentService.getAllByUserId(userId);
        Long like =0L;
        postResponseDTOS.stream().map(e -> like + e.getReactionDTO().getLike()).collect(Collectors.toList());
        commentResponseDTOS.stream().map(e -> like + e.getReactionDTO().getLike()).collect(Collectors.toList());

        Long dislike =0L;
        postResponseDTOS.stream().map(e -> dislike + e.getReactionDTO().getDislike()).collect(Collectors.toList());
        commentResponseDTOS.stream().map(e -> dislike + e.getReactionDTO().getDislike()).collect(Collectors.toList());

        ReactionDTO reactionDTO = ReactionDTO.builder().like(like).dislike(dislike).build();
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, reactionDTO);
    }
}
