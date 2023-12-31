package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
public class HealthInfoDTO {
    private Long height;
    private Long weight;
    private Long sleepTime;
    private Long trainingTime;
}
