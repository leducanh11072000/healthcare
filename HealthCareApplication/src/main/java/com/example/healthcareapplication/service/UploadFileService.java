package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.dto.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UploadFileService {
    DataResponse uploadFile (MultipartFile file) throws IOException;

    DataResponse getFile(String path) throws IOException;
    ResponseEntity displayImages(String path) throws IOException;
}
