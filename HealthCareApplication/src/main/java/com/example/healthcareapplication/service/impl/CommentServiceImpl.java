package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.Utils.DataUtils;
import com.example.healthcareapplication.model.*;
import com.example.healthcareapplication.model.dto.*;
import com.example.healthcareapplication.repository.CommentRepository;
import com.example.healthcareapplication.repository.ReactionRepository;
import com.example.healthcareapplication.service.CommentService;
import com.example.healthcareapplication.service.ReactionService;
import com.example.healthcareapplication.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final ReactionService reactionService;
    @Autowired
    private final UserService userService;
    @Override
    public DataResponse getByPostId(Long postId, Long pageNo, Long pageSize) {

        try {
            List<Comment> comments = commentRepository.getAllByPostIdAndStatus(postId, Common.ACTIVE_STATUS,DataUtils.getPageable(pageNo,pageSize));
            List<CommentResponseDTO> commentResponseDTOList = convertFromComment(comments);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,commentResponseDTOList);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse insertComment(CommentDTO commentDTO) {
        try {
            Reaction reaction = reactionService.save(Reaction.builder()
                    .dislike(0L)
                    .like(0L)
                    .build());
            Comment comment = Comment.builder()
                    .content(commentDTO.getContent())
                    .parentId(commentDTO.getParentId())
                    .postId(commentDTO.getPostId())
                    .userId(commentDTO.getUserId())
                    .status(Common.ACTIVE_STATUS)
                    .reactionId(reaction.getId())
                    .createTime(LocalDateTime.now())
                    .build();
            Comment result = commentRepository.save(comment);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse updateComment(CommentDTO commentDTO) {
        try {
            Comment comment = commentRepository.findById(commentDTO.getId()).orElse(null);
            if (comment == null || comment.getUserId().equals(commentDTO.getUserId())) {
                log.error("Không tìm thấy thông tin bình luận hoặc người dùng không có quyền sửa");
                return new DataResponse(HttpStatus.NOT_FOUND.value(),"Không tìm thấy thông tin bình luận hoặc người dùng không có quyền sửa",null);
            }
            comment.setContent(commentDTO.getContent());
            Comment result = commentRepository.save(comment);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse deleteComment(CommentDTO commentDTO) {
        try {
            Comment comment = commentRepository.findById(commentDTO.getId()).orElse(null);
            if (comment == null || comment.getUserId().equals(commentDTO.getUserId())) {
                log.error("Không tìm thấy thông tin bình luận hoặc người dùng không có quyền xóa");
                return new DataResponse(HttpStatus.NOT_FOUND.value(), "Không tìm thấy thông tin bình luận hoặc người dùng không có quyền xóa",null);
            }
            comment.setStatus(Common.INACTIVE_STATUS);
            Comment result = commentRepository.save(comment);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public List<CommentResponseDTO> getByPostIdInside(Long postId) {
        List<Comment> comments = commentRepository.getAllByPostIdAndStatus(postId, Common.ACTIVE_STATUS);
        List<CommentResponseDTO> commentResponseDTOList = convertFromComment(comments);
        return commentResponseDTOList;
    }

    private List<CommentResponseDTO> convertFromComment(List<Comment> comments) {
        return comments.stream().map(comment -> {
            Reaction reaction = reactionService.getById(comment.getReactionId());
            User user = userService.getUserById(comment.getUserId());
            return CommentResponseDTO.builder()
                    .userDTO(DataUtils.convertUserToUserDTO(user))
                    .reactionDTO(ReactionDTO.builder()
                            .like(reaction.getLike())
                            .dislike(reaction.getDislike())
                            .build())
                    .id(comment.getId())
                    .reactionId(comment.getReactionId())
                    .parentid(comment.getParentId())
                    .content(comment.getContent())
                    .createTime(comment.getCreateTime())
                    .build();
        }).collect(Collectors.toList());
    }
}
