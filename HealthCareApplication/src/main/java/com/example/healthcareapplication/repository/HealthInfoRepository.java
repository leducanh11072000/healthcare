package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthInfoRepository   extends JpaRepository<HealthInfo, Long> {
    List<HealthInfo> findAllByCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(LocalDateTime startDate,LocalDateTime endDate);
}
