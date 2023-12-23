package com.example.healthcareapplication.controller;

import com.example.healthcareapplication.customAnnotation.RestApiV1;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.service.UploadFileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin(origins = "*")
@RestApiV1
@AllArgsConstructor
public class UploadFileConntroller {
    @Autowired
    private final UploadFileService uploadFileService;
    @PostMapping("/upload")
    DataResponse uploadFile(@Valid @NotNull @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return uploadFileService.uploadFile(multipartFile);
    }

    @GetMapping("/upload")
    DataResponse getFileByAbsolutePath(@RequestParam(value = "path") String path) throws IOException {
        return uploadFileService.getFile(path);
    }
}
