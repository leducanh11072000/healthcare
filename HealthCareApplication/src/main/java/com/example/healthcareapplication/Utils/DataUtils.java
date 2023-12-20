package com.example.healthcareapplication.Utils;


import com.example.healthcareapplication.model.User;
import com.example.healthcareapplication.model.dto.UserInfoDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class DataUtils {
    public static Pageable getPageable(Long pageNo, Long pageSize){
        return PageRequest.of(pageNo.intValue(),pageSize.intValue());
    }
    public static UserInfoDTO convertUserToUserDTO(User user) {
        return UserInfoDTO.builder()
                .avatarAddress(user.getAvatarAddress())
                .id(user.getId())
                .tagName(user.getTagName())
                .build();
    }
}
