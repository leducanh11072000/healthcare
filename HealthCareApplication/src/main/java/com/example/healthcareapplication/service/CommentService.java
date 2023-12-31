package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.dto.CommentResponseDTO;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.CommentDTO;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface CommentService {
    DataResponse getByPostId(Long postId, Long pageNo, Long pageSize);
    DataResponse insertComment(CommentDTO commentDTO);

    DataResponse updateComment(CommentDTO commentDTO);

    DataResponse deleteComment(CommentDTO commentDTO);
    List<CommentResponseDTO> getByPostIdInside(Long postId);
    List<CommentResponseDTO> getAllByUserId(Long userId);

}
