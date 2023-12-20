package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
    Post getPostByIdAndStatus(Long id, Long status);
    List<Post> getAllByUserIdAndStatus(Long userId, Pageable pageable, Long status);
    List<Post> getAllByTagsIdAndStatus(String tagId, Long status, Pageable pageable);

}
