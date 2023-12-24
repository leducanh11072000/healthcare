package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.Utils.DataUtils;
import com.example.healthcareapplication.model.*;
import com.example.healthcareapplication.model.dto.*;
import com.example.healthcareapplication.repository.PostRepository;
import com.example.healthcareapplication.service.PostService;
import com.example.healthcareapplication.service.ReactionService;
import com.example.healthcareapplication.service.TagService;
import com.example.healthcareapplication.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final TagService tagService;
    @Autowired
    private final ReactionService reactionService;
    @Autowired
    private final UserService userService;
    @Override
    public DataResponse getAllByUserId(Long userId, Long pageNo, Long pageSize) {
        try {
            List<Post> postList = postRepository.getAllByUserIdAndStatus(userId, DataUtils.getPageable(pageNo, pageSize),Common.ACTIVE_STATUS);
            List<PostResponseDTO> postResponseDTOS = convertFromPost(postList);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,postResponseDTOS);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse getSome(Long pageNo, Long pageSize) {
        try {
            List<Post> postList = postRepository.findAll(DataUtils.getPageable(pageNo, pageSize)).stream().filter(post -> Common.ACTIVE_STATUS.equals(post.getStatus()) && post.getUserId()!=null).toList();
            List<PostResponseDTO> postResponseDTOS = convertFromPost(postList);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,postResponseDTOS);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse insertPost(InsertPostDTO insertPostDTO) {
        try {
            StringJoiner stringJoiner = new StringJoiner(", ");
            if (insertPostDTO.getTags() != null) {
                List<String> listTagNameBeforeValidate = Arrays.stream(insertPostDTO.getTags().split(",")).toList();
                if (listTagNameBeforeValidate != null) {

                    listTagNameBeforeValidate.stream().map(e -> {
                        Tag tag;
                        if (tagService.isExist(e)) {
                            tag = tagService.getByName(e);
                            stringJoiner.add(tag.getId().toString());
                        } else {
                            tag = tagService.save(Tag.builder().name(e).build());
                        }
                        stringJoiner.add(tag.getId().toString());
                        return tag;
                    }).toList();
                }
            }
            String listTagId = stringJoiner.toString();
            Reaction reaction = reactionService.save(Reaction.builder()
                    .like(0L)
                    .dislike(0L)
                    .build());
            Post post = Post.builder()
                    .status(Common.ACTIVE_STATUS)
                    .title(insertPostDTO.getTitle())
                    .userId(insertPostDTO.getUserId())
                    .tagsId(listTagId)
                    .reactionId(reaction.getId())
                    .content(insertPostDTO.getContent())
                    .updateBy(insertPostDTO.getUserName())
                    .createBy(insertPostDTO.getUserName())
                    .watchTime(0L)
                    .build();
            Post result = postRepository.save(post);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse getSomeByTagId(Long tagId, Long pageNo, Long pageSize) {
        try {
            StringJoiner stringJoinerWithPrefixSufix = new StringJoiner(",", "%", "%");
            stringJoinerWithPrefixSufix.add(tagId.toString());
            String keyword = stringJoinerWithPrefixSufix.toString();
            List<Post> postList = postRepository.getAllByTagsIdAndStatus(keyword,Common.ACTIVE_STATUS,DataUtils.getPageable(pageNo, pageSize)).stream().toList();
            List<PostResponseDTO> postResponseDTOS = convertFromPost(postList);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,postResponseDTOS);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    @Override
    public DataResponse updatePost(UpdatePostDTO updatePostDTO) {
        Post post = postRepository.getPostByIdAndStatus(updatePostDTO.getPostId(),Common.ACTIVE_STATUS);
        if (post == null || post.getUserId().equals(updatePostDTO.getUserId())) {
            log.error("Không tìm thấy thông tin bài viết hoặc người dùng không có quyền sửa");
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),"Không tìm thấy thông tin bài viết hoặc người dùng không có quyền sửa",null);
        }
        post.setContent(updatePostDTO.getNewContent());
        post.setTitle(updatePostDTO.getNewTitle());
        postRepository.save(post);
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,post);
    }

    @Override
    public DataResponse deletePost(DeletePostDTO deletePostDTO) {
        Post post = postRepository.getPostByIdAndStatus(deletePostDTO.getPostId(),Common.ACTIVE_STATUS);
        if (post == null || post.getUserId().equals(deletePostDTO.getUserId())) {
            log.error("Không tìm thấy thông tin bài viết hoặc người dùng không có quyền xóa");
            return new DataResponse(HttpStatus.NOT_FOUND.value(), "Không tìm thấy thông tin bài viết hoặc người dùng không có quyền xóa",null);
        }
        post.setStatus(Common.INACTIVE_STATUS);
        postRepository.save(post);
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,post);
    }

    private List<PostResponseDTO> convertFromPost(List<Post> postList) {
        return postList.stream().map(post -> {
            Reaction reaction = reactionService.getById(post.getReactionId());
            User user = userService.getUserById(post.getUserId());
            List<Tag> tags;
            if (post.getTagsId() == null || "".equals(post.getTagsId())) {
            }
            tags = Arrays.stream(post.getTagsId().split(",")).map(String::trim).map(id -> {
                Tag tag = tagService.getById(Long.getLong(id));
                return tag;
            }).toList();
            Map<Long,String> map = tags.stream()
                    .collect(Collectors.toMap(
                            Tag::getId,        // Key: original element
                            Tag::getName     // Value: squared value
                    ));
            return PostResponseDTO.builder()
                    .postId(post.getId())
                    .reactionDTO(ReactionDTO.builder()
                            .like(reaction.getLike())
                            .dislike(reaction.getDislike())
                            .build())
                    .reactionId(post.getReactionId())
                    .userDTO(DataUtils.convertUserToUserDTO(user))
                    .tagName(map)
                    .postId(post.getUserId())
                    .createTime(post.getCreateTime())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build();
        }).collect(Collectors.toList());
    }
}
