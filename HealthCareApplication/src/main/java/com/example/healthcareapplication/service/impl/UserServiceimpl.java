package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.User;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.RegisterUserDTO;
import com.example.healthcareapplication.model.dto.UserInfoDTO;
import com.example.healthcareapplication.model.dto.UserLoginDto;
import com.example.healthcareapplication.repository.UserRepository;
import com.example.healthcareapplication.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@AllArgsConstructor
@Slf4j
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public DataResponse login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserName(userLoginDto.getUserName()).orElse(null);
        if (user ==null) return new DataResponse(HttpStatus.BAD_REQUEST, "Tên người dùng hoặc mật khẩu không đúng", null);
        if (user.getPassword().equals(userLoginDto.getPassword())) {
            return new DataResponse(HttpStatus.OK, Common.SUCCESS, UserInfoDTO.builder()
                    .id(user.getId())
                    .tagName(user.getTagName())
                    .avatarAddress(user.getAvatarAddress())
                    .email(user.getEmail())
                    .username(user.getUserName())
                    .build());
        } else {
            return new DataResponse(HttpStatus.BAD_REQUEST, "Tên người dùng hoặc mật khẩu không đúng", null);
        }
    }

    @Override
    public DataResponse register(RegisterUserDTO registerUserDTO) {
        if (registerUserDTO.getPassword().equals(registerUserDTO.getRepeatPassword())) {
            return new DataResponse(HttpStatus.BAD_REQUEST, "nhập lại mật khẩu không trùng khớp, xin kiểm tra lại", null);
        }
        User user = userRepository.findByUserName(registerUserDTO.getUsername()).orElse(null);
        if (user !=null) return new DataResponse(HttpStatus.BAD_REQUEST, "tên đăng nhập đã tồn tại", null);

        User userEmail = userRepository.findByEmail(registerUserDTO.getEmail()).orElse(null);
        if (userEmail !=null) return new DataResponse(HttpStatus.BAD_REQUEST, "email đã dược đăng kí", null);

        User newUser = User.builder()
                .userName(registerUserDTO.getUsername())
                .email(registerUserDTO.getEmail())
                .password(registerUserDTO.getPassword())
                .avatarAddress("cc")
                .tagName("@"+registerUserDTO.getUsername())
                .build();
        return new DataResponse(HttpStatus.OK, Common.SUCCESS, userRepository.save(newUser));

    }
}
