package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.service.ReactionHistoryService;
import com.example.healthcareapplication.service.ReactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@AllArgsConstructor
public class ReactionController {
    @Autowired
    private final ReactionHistoryService reactionHistoryService;

    @GetMapping("/reaction/{userId}")
    DataResponse getUserReactionHistory (@Valid @NotNull @Positive @PathVariable Long userId){
        return reactionHistoryService.getReactionHistoryByUserId(userId);
    }
}
