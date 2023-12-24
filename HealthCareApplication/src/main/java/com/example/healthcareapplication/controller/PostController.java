package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.DeletePostDTO;
import com.example.healthcareapplication.model.dto.InsertPostDTO;
import com.example.healthcareapplication.model.dto.UpdatePostDTO;
import com.example.healthcareapplication.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private PostService postService;


    @GetMapping("/post/user/{userId}")
    DataResponse getAllByUserId (@Valid @NotNull @Positive @PathVariable Long userId, @RequestParam @Positive Long pageNo,@RequestParam @Positive Long pageSize) {
        return postService.getAllByUserId(userId,pageNo,pageSize);
    };
    @GetMapping("/post")
    DataResponse getSome(@RequestParam Long pageNo, @RequestParam @Positive Long pageSize){
        return postService.getSome(pageNo,pageSize);
    }

    @PostMapping("/post")
    DataResponse insertPost(@RequestBody InsertPostDTO insertPostDTO) {
        return postService.insertPost(insertPostDTO);
    }

    @GetMapping("/post/tag/{tagId}")
    DataResponse getSomeByTagId(@Valid @NotNull @Positive @PathVariable Long tagId, @RequestParam @Positive Long pageNo,@RequestParam @Positive Long pageSize){
        return postService.getSomeByTagId(tagId,pageNo, pageSize);
    }

    @PutMapping("/post")
    DataResponse updatePost(@RequestBody UpdatePostDTO updatePostDTO) {
        return postService.updatePost(updatePostDTO);
    }

    @DeleteMapping ("/post")
    DataResponse updatePost(@RequestBody DeletePostDTO deletePostDTO) {
        return postService.deletePost(deletePostDTO);
    }
    @PostMapping("/post/increase-look-time")
    DataResponse increase(@RequestParam Long postId) {
        return postService.increaseWatchTime(postId);
    }
}
