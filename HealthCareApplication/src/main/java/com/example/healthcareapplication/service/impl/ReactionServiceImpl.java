package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Reaction;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.repository.ReactionHistoryRepository;
import com.example.healthcareapplication.repository.ReactionRepository;
import com.example.healthcareapplication.service.ReactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReactionServiceImpl implements ReactionService {
    @Autowired
    private final ReactionRepository reactionRepository;
    @Override
    public Reaction getById(Long id) {
        return reactionRepository.findById(id).orElse(null);
    }

    @Override
    public Reaction save(Reaction reaction) {
        return reactionRepository.save(reaction);
    }
}
