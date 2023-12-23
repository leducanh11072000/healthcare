package com.example.healthcareapplication.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class HealthInfoResponseDTO {
    private Double value;
    private Long time;
}
