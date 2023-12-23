package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.RegisterUserDTO;
import com.example.healthcareapplication.model.dto.UserInfoDTO;
import com.example.healthcareapplication.model.dto.UserLoginDto;
import com.example.healthcareapplication.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestApiV1
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/account/register")
public class UserController {
    private final UserService userService;
    @PostMapping("/user/login")
    DataResponse login(@RequestBody UserLoginDto userLoginDto){
        return  userService.login(userLoginDto);
    }

    @PostMapping("/user/register")
    DataResponse register(@RequestBody RegisterUserDTO registerUserDTO){
        return  userService.register(registerUserDTO);
    }
    @PostMapping("/user/update-avatar")
    DataResponse register(@RequestParam Long userId,@Valid @NotNull @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return  userService.updateAvatar(userId,multipartFile);
    }
}
