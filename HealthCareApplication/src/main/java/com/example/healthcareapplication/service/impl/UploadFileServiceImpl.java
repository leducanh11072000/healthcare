package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Common;
import com.example.healthcareapplication.model.dto.DataResponse;
import com.example.healthcareapplication.service.UploadFileService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, file.getOriginalFilename());
    }

    @Override
    public DataResponse getFile(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(path));
        String base64EncodedString = Base64.encodeBase64String(fileContent);
        return new DataResponse(HttpStatus.OK.value(), Common.SUCCESS, base64EncodedString);
    }

    @Override
    public ResponseEntity displayImages(String path) throws IOException {
        byte [] imageBytes = Files.readAllBytes(Paths.get(path));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
