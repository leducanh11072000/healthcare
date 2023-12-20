package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.RegisterUserDTO;
import com.example.healthcareapplication.model.dto.UserInfoDTO;
import com.example.healthcareapplication.model.dto.UserLoginDto;
import com.example.healthcareapplication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/user/login")
    DataResponse login(@RequestBody UserLoginDto userLoginDto){
        return  userService.login(userLoginDto);
    }

    @PostMapping("/user/register")
    DataResponse register(RegisterUserDTO registerUserDTO){
        return  userService.register(registerUserDTO);
    }
}
