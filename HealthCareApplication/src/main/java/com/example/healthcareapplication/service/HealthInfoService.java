package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.HealthInfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface HealthInfoService {
    DataResponse insertByUserId(Long userId, HealthInfoDTO healthInfoDTO);
    DataResponse findAvgInYear(Long userId,Long type);
    DataResponse findAvgInMonth(Long userId,Long type);

    DataResponse getByUserId(Long userId);
}
