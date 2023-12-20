package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.CommentDTO;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public interface CommentService {
    DataResponse getByPostId(@RequestParam Long postId, @RequestParam Long pageNo, @RequestParam Long pageSize);
    DataResponse insertComment(@RequestBody CommentDTO commentDTO);

    DataResponse updateComment(CommentDTO commentDTO);

    DataResponse deleteComment(CommentDTO commentDTO);
}
