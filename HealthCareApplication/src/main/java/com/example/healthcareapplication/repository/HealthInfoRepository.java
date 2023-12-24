package com.example.healthcareapplication.repository;

import com.example.healthcareapplication.model.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthInfoRepository   extends JpaRepository<HealthInfo, Long> {
    List<HealthInfo> findAllByCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(LocalDateTime startDate,LocalDateTime endDate);
    @Query(value = "SELECT AVG(e.value) FROM health_info e WHERE e.user_id = ?1 AND e.type = ?2 AND WHERE MONTH(e.create_time) = MONTH(?3)\n" +
            "AND YEAR(e.create_time) = YEAR(?3);" , nativeQuery = true)
    Double findAvgInMonthByUserIdAndTypeAndCreateTime(Long userId,Long type, LocalDateTime date);

    @Query(value = "SELECT AVG(e.value) FROM health_info e WHERE e.user_id = ?1 AND e.type = ?2 WHERE YEARWEEK(e.create_time) = YEARWEEK(?3);" , nativeQuery = true)
    Double findAvgInWeekByUserIdAndTypeAndCreateTime(Long userId,Long type, LocalDateTime date);
    HealthInfo findTopByCreateTimeDescAndUserIdAndAndType(Long userId, Long type);

}
