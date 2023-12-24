package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.HealthInfo;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.HealthInfoDTO;
import com.example.healthcareapplication.model.dto.HealthInfoResponseDTO;
import com.example.healthcareapplication.repository.HealthInfoRepository;
import com.example.healthcareapplication.service.HealthInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
@Service
public class HealthInfoServiceImpl implements HealthInfoService {
    @Autowired
    private final HealthInfoRepository healthInfoRepository;
    @Override
    public DataResponse insertByUserId(Long userId, HealthInfoDTO healthInfoDTO) {
        LocalDateTime now = LocalDateTime.now();
        HealthInfo height = HealthInfo.builder()
                .value(healthInfoDTO.getHeight().doubleValue())
                .type(Common.HEALTH_INFO.HEIGHT)
                .userId(userId)
                .createTime(now)
                .status(Common.ACTIVE_STATUS)
                .build();
        HealthInfo weight =  HealthInfo.builder()
                .value(healthInfoDTO.getWeight().doubleValue())
                .type(Common.HEALTH_INFO.WEIGHT)
                .userId(userId)
                .createTime(now)
                .status(Common.ACTIVE_STATUS)
                .build();
        Double bmiValue = (healthInfoDTO.getHeight() / (healthInfoDTO.getWeight() * healthInfoDTO.getWeight()) *10000.0) ;
        HealthInfo bmi =  HealthInfo.builder()
                .value(bmiValue)
                .type(Common.HEALTH_INFO.BMI)
                .userId(userId)
                .createTime(now)
                .status(Common.HEALTH_INFO.BMI)
                .build();
        HealthInfo sleepTime =  HealthInfo.builder()
                .value(healthInfoDTO.getSleepTime().doubleValue())
                .type(Common.HEALTH_INFO.SLEEP_TIME)
                .userId(userId)
                .createTime(now)
                .status(Common.ACTIVE_STATUS)
                .build();
        HealthInfo trainingTime = HealthInfo.builder()
                .value(healthInfoDTO.getTrainingTime().doubleValue())
                .type(Common.HEALTH_INFO.TRAINING_TIME)
                .userId(userId)
                .createTime(now)
                .status(Common.ACTIVE_STATUS)
                .build();
        List<HealthInfo> healthInfoList = Stream.of(height,weight,bmi,sleepTime,trainingTime).toList();
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,healthInfoRepository.saveAll(healthInfoList));
    }

    @Override
    public DataResponse findAvgInYear(Long userId, Long type) {
        LocalDateTime now = LocalDateTime.now();
        List<HealthInfoResponseDTO> healthInfoDTOS = new ArrayList<>();
        for (int i =0; i<8;i++) {
            Double value = healthInfoRepository.findAvgInMonthByUserIdAndTypeAndCreateTime(userId,type, now.minusMonths(i));
            Long time = (long) now.minusMonths(i).getMonthValue();
            HealthInfoResponseDTO healthInfoResponseDTO = HealthInfoResponseDTO.builder()
                    .value(value)
                    .time(time)
                    .build();
            healthInfoDTOS.add(healthInfoResponseDTO);
        }
        healthInfoDTOS = healthInfoDTOS.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,healthInfoDTOS);
    }

    @Override
    public DataResponse findAvgInMonth(Long userId, Long type) {
        LocalDateTime now = LocalDateTime.now();
        List<HealthInfoResponseDTO> healthInfoDTOS = new ArrayList<>();
        for (int i =0; i<8;i++) {
            Double value = healthInfoRepository.findAvgInWeekByUserIdAndTypeAndCreateTime(userId,type, now.minusWeeks(i));
            Long time = (long) now.minusWeeks(i).getMonthValue();
            HealthInfoResponseDTO healthInfoResponseDTO = HealthInfoResponseDTO.builder()
                    .value(value)
                    .time(time)
                    .build();
            healthInfoDTOS.add(healthInfoResponseDTO);
        }
        healthInfoDTOS = healthInfoDTOS.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS,healthInfoDTOS);
    }

    @Override
    public DataResponse getByUserId(Long userId) {
        try {
            List<HealthInfo> healthInfos = new ArrayList<>();
            for (Long i = 1L; i < 6L; i++) {
                healthInfos.add(healthInfoRepository.findTopByAndUserIdAndAndTypeOrderByCreateTimeDesc(userId, i));
            }
            return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, healthInfos);
        } catch (Exception e) {
            return new DataResponse(HttpStatus.NOT_FOUND.value(), "Có lỗi xảy ra trong khi tìm kiếm thông tin", null);
        }
    }
}
