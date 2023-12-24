package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.DeletePostDTO;
import com.example.healthcareapplication.model.dto.InsertPostDTO;
import com.example.healthcareapplication.model.dto.UpdatePostDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public interface PostService {
    DataResponse getAllByUserId (Long userId, @Positive Long pageNo, @Positive Long pageSize);
    DataResponse getSome(Long pageNo,Long pageSize);
    DataResponse insertPost(InsertPostDTO insertPostDTO);
    DataResponse getSomeByTagId(Long tagId,Long pageNo, Long pageSize);

    DataResponse updatePost(UpdatePostDTO updatePostDTO);

    DataResponse deletePost(DeletePostDTO deletePostDTO);

    DataResponse increaseWatchTime(Long postId);

    DataResponse getPostById(Long postId);
}
