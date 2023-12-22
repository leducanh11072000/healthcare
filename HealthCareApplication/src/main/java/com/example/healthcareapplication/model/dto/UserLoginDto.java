package com.example.healthcareapplication.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserLoginDto {
  @NotBlank(message = "userName must be not blank")
  protected String username;
  @NotBlank(message = "password must be not blank")
  protected String password;
}
