package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.CommentDTO;
import com.example.healthcareapplication.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private final CommentService commentService;


    @GetMapping("/comment/{postId}")
    DataResponse getByPostId(@PathVariable Long postId, @RequestParam Long pageNo, @RequestParam Long pageSize){
        return  commentService.getByPostId(postId,pageNo,pageSize);
    }

    @PostMapping("/comment")
    DataResponse insertComment(@RequestBody CommentDTO commentDTO){
        return  commentService.insertComment(commentDTO);
    }

    @PutMapping("/comment")
    DataResponse updateComment(@RequestBody CommentDTO commentDTO){
        return  commentService.updateComment(commentDTO);
    }

    @DeleteMapping ("/comment")
    DataResponse udeleteComment(@RequestBody CommentDTO commentDTO){
        return  commentService.deleteComment(commentDTO);
    }
}
