package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.Reaction;
import com.example.healthcareapplication.model.ReactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionHistoryRepository  extends JpaRepository<ReactionHistory, Long> {
    List<ReactionHistory> findAllByUserIdAndStatus(Long userId,Long status);
}
