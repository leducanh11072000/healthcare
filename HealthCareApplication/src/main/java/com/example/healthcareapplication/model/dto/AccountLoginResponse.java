package com.example.healthcareapplication.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AccountLoginResponse {
  private String token;

  private String avatar;

  private String tagName;

  private String email;

  protected LocalDateTime createTime;
}
