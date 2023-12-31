package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPostIdAndStatus(Long postId, Long status, Pageable pageable);
    List<Comment> getAllByPostIdAndStatus(Long postId, Long status);

    List<Comment> getAllByUserIdAndStatus(Long userId, Long activeStatus);
}
