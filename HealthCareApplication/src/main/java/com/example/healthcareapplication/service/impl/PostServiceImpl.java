package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.Utils.DataUtils;
import com.example.healthcareapplication.model.*;
import com.example.healthcareapplication.model.dto.*;
import com.example.healthcareapplication.repository.PostRepository;
import com.example.healthcareapplication.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    @Autowired
    private final CommentService commentService;
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
            String listTagId =null;
            if (insertPostDTO.getTags() != null) {
                StringJoiner stringJoiner = new StringJoiner(",");
                List<String> listTagNameBeforeValidate = Arrays.stream(insertPostDTO.getTags().trim().split(",")).filter(e->!"".equals(e)).toList();
                listTagNameBeforeValidate.stream().map(e -> {
                    Tag tag;
                    if (tagService.isExist(e)) {
                        tag = tagService.getByName(e);
                    } else {
                        tag = tagService.save(Tag.builder().name(e).build());
                    }
                    stringJoiner.add(tag.getId().toString());
                    return tag;
                }).toList();
                listTagId = stringJoiner.toString();
            }
            Reaction reaction = reactionService.save(Reaction.builder()
                    .like(0L)
                    .dislike(0L)
                    .isPost(true)
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
                    .createTime(LocalDateTime.now())
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
        if (post == null || !post.getUserId().equals(updatePostDTO.getUserId())) {
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
        if (post == null || !post.getUserId().equals(deletePostDTO.getUserId())) {
            log.error("Không tìm thấy thông tin bài viết hoặc người dùng không có quyền xóa");
            return new DataResponse(HttpStatus.NOT_FOUND.value(), "Không tìm thấy thông tin bài viết hoặc người dùng không có quyền xóa",null);
        }
        post.setStatus(Common.INACTIVE_STATUS);
        postRepository.save(post);
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,post);
    }

    @Override
    public DataResponse increaseWatchTime(Long postId) {
        Post post = postRepository.getPostByIdAndStatus(postId,Common.ACTIVE_STATUS);
        if (post == null ) {
            log.error("Không tìm thấy thông tin bài viết ");
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),"Không tìm thấy thông tin bài viết",null);
        }
        post.setWatchTime(post.getWatchTime()+1L);
        postRepository.save(post);
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,post);
    }

    @Override
    public DataResponse getPostById(Long postId) {
        Post post = postRepository.getPostByIdAndStatus(postId,Common.ACTIVE_STATUS);
        if (post == null ) {
            log.error("Không tìm thấy thông tin bài viết ");
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),"Không tìm thấy thông tin bài viết",null);
        }
        List<PostResponseDTO> postResponseDTOS = convertFromPost(Collections.singletonList(post));
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,postResponseDTOS);
    }

    @Override
    public DataResponse findByKeyword(String keyword, Long pageNo, Long pageSize) {
        try {
            List<Post> postList = postRepository.findByTitleContainingAndStatus(keyword.trim(),Common.ACTIVE_STATUS,DataUtils.getPageable(pageNo, pageSize)).stream().filter(post -> Common.ACTIVE_STATUS.equals(post.getStatus()) && post.getUserId()!=null).toList();
            List<PostResponseDTO> postResponseDTOS = convertFromPost(postList);
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,postResponseDTOS);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new DataResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private List<PostResponseDTO> convertFromPost(List<Post> postList) {
        return postList.stream().map(post -> {
            Reaction reaction = reactionService.getById(post.getReactionId());
            User user = userService.getUserById(post.getUserId());
            List<Tag> tags = null;
            if (post.getTagsId() != null && !"".equals(post.getTagsId())) {
                tags = Arrays.stream(post.getTagsId().split(",")).map(String::trim).map(id -> {
                    Tag tag = tagService.getById(Long.parseLong(id));
                    return tag;
                }).toList();
            }
            Map<Long, String> map = null;
            if (tags != null) {
             map = tags.stream()
                    .collect(Collectors.toMap(
                            Tag::getId,        // Key: original element
                            Tag::getName     // Value: squared value
                    ));
            }
            List<CommentResponseDTO> commentResponseDTOList = commentService.getByPostIdInside(post.getId());
            return PostResponseDTO.builder()
                    .postId(post.getId())
                    .reactionDTO(ReactionDTO.builder()
                            .like(reaction.getLike())
                            .dislike(reaction.getDislike())
                            .build())
                    .reactionId(post.getReactionId())
                    .userDTO(DataUtils.convertUserToUserDTO(user))
                    .tagName(map)
                    .createTime(post.getCreateTime())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .countComment((long) commentResponseDTOList.size())
                    .watchTime(post.getWatchTime())
                    .build();
        }).collect(Collectors.toList());
    }
}
