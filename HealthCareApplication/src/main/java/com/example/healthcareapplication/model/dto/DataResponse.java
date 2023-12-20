package com.example.healthcareapplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
