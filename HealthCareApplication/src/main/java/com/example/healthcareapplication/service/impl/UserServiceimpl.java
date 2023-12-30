package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.User;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.RegisterUserDTO;
import com.example.healthcareapplication.model.dto.UserInfoDTO;
import com.example.healthcareapplication.model.dto.UserLoginDto;
import com.example.healthcareapplication.repository.UserRepository;
import com.example.healthcareapplication.service.UploadFileService;
import com.example.healthcareapplication.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UploadFileService uploadFileService;
    final List<String> IMAGE_DEFAULt = Arrays.asList("images1.jpg","images2.jpg","images3.jpg","images4.jpg");
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public DataResponse login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserName(userLoginDto.getUsername()).orElse(null);
        if (user == null) return new DataResponse(HttpStatus.NOT_FOUND.value(), "Tên người dùng hoặc mật khẩu không đúng", null);
        if (user.getPassword().equals(userLoginDto.getPassword())) {
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, UserInfoDTO.builder()
                    .id(user.getId())
                    .tagName(user.getTagName())
                    .avatarAddress(user.getAvatarAddress())
//                    .email(user.getEmail())
                    .username(user.getUserName())
                    .build());
        } else {
            return new DataResponse(HttpStatus.BAD_REQUEST.value(), "Tên người dùng hoặc mật khẩu không đúng", null);
        }
    }

    @Override
    public DataResponse register(RegisterUserDTO registerUserDTO) {
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getRepeatPassword())) {
            return new DataResponse(HttpStatus.BAD_REQUEST.value(), "nhập lại mật khẩu không trùng khớp, xin kiểm tra lại", null);
        }
        User user = userRepository.findByUserName(registerUserDTO.getUsername()).orElse(null);
        if (user !=null) return new DataResponse(HttpStatus.BAD_REQUEST.value(), "tên đăng nhập đã tồn tại", null);

        User userEmail = userRepository.findByEmail(registerUserDTO.getEmail()).orElse(null);
        if (userEmail !=null) return new DataResponse(HttpStatus.BAD_REQUEST.value(), "email đã dược đăng kí", null);

        User newUser = User.builder()
                .userName(registerUserDTO.getUsername())
                .email(registerUserDTO.getEmail())
                .password(registerUserDTO.getPassword())
                .avatarAddress(IMAGE_DEFAULt.stream().findAny().orElse(IMAGE_DEFAULt.get(0)))
                .tagName("@"+registerUserDTO.getUsername())
                .build();
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, userRepository.save(newUser));

    }

    @Override
    public DataResponse updateAvatar(Long userId, MultipartFile multipartFile) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        if (user ==null) return new DataResponse(HttpStatus.BAD_REQUEST.value(), "Không tồn tại user", null);
        DataResponse response = uploadFileService.uploadFile(multipartFile);
        user.setAvatarAddress(response.getData().toString());
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, userRepository.save(user));
    }
}
