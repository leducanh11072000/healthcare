package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.Reaction;
import org.springframework.stereotype.Service;
@Service
public interface ReactionService {
    Reaction getById(Long id);
    Reaction save(Reaction reaction);
}
