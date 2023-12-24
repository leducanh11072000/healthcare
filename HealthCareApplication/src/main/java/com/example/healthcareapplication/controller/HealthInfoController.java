package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.model.dto.HealthInfoDTO;
import com.example.healthcareapplication.service.CommentService;
import com.example.healthcareapplication.service.HealthInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class HealthInfoController {
    @Autowired
    private final HealthInfoService healthInfoService;

    @PostMapping("/health-info")
    DataResponse inserHealthInfo(@RequestParam Long userId, @RequestBody HealthInfoDTO healthInfoDTO){
        return  healthInfoService.insertByUserId(userId,healthInfoDTO);
    }

    @GetMapping("/health-info/month")
    DataResponse showAVGHealthInfoYear(@RequestParam Long userId, @RequestParam Long type){
        return  healthInfoService.findAvgInYear(userId,type);
    }

    @GetMapping("/health-info/weak")
    DataResponse showAVGHealthInfoWeak(@RequestParam Long userId, @RequestParam Long type){
        return  healthInfoService.findAvgInMonth(userId,type);
    }


}
