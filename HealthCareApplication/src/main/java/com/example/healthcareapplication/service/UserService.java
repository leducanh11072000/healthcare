package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.User;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.RegisterUserDTO;
import com.example.healthcareapplication.model.dto.UserInfoDTO;
import com.example.healthcareapplication.model.dto.UserLoginDto;
import org.springframework.stereotype.Service;
@Service
public interface UserService {
    User getUserById(Long id);
    DataResponse login(UserLoginDto userLoginDto);
    DataResponse register(RegisterUserDTO registerUserDTO);
}
