package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.HealthInfoDTO;
import com.example.healthcareapplication.repository.HealthInfoRepository;
import com.example.healthcareapplication.service.HealthInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class HealthInfoServiceImpl implements HealthInfoService {
    @Autowired
    private final HealthInfoRepository healthInfoRepository;
    @Override
    public DataResponse insertByUserId(Long userId, HealthInfoDTO healthInfoDTO) {
        return null;
    }

    @Override
    public DataResponse findAvgInYear(Long userId, Long type) {
        return null;
    }

    @Override
    public DataResponse findAvgInMonth(Long userId, Long type) {
        return null;
    }
}
