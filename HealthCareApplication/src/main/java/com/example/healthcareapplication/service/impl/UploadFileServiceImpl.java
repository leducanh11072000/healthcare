package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.service.UploadFileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Override
    public DataResponse uploadFile(MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(Common.UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, fileNameAndPath.toString());
    }
}