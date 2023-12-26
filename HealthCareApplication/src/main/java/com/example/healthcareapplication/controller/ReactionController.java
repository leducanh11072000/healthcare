package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.CreateReactionDTO;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.service.ReactionHistoryService;
import com.example.healthcareapplication.service.ReactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReactionController {
    @Autowired
    private final ReactionHistoryService reactionHistoryService;

    @GetMapping("/reaction/{userId}")
    DataResponse getUserReactionHistory (@Valid @NotNull @Positive @PathVariable Long userId){
        return reactionHistoryService.getReactionHistoryByUserId(userId);
    }

    @PostMapping("/reaction")
    DataResponse reactionPost (@RequestBody CreateReactionDTO createReactionDTO){
        return reactionHistoryService.createHistory(createReactionDTO);
    }
}
