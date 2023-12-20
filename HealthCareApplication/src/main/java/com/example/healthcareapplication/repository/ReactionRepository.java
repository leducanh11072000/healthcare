package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository  extends JpaRepository<Reaction, Long> {
}
