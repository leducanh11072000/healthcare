package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.Reaction;
import com.example.healthcareapplication.model.dto.DataResponse;
import org.springframework.stereotype.Service;
@Service
public interface ReactionService {
    Reaction getById(Long id);
    Reaction save(Reaction reaction);
}
