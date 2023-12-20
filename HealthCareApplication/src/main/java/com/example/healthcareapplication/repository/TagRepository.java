package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {
    Tag getAllByNameEqualsIgnoreCase(String name);
}
