package com.example.healthcareapplication.service;

import com.example.healthcareapplication.model.Tag;
import org.springframework.stereotype.Service;
@Service
public interface TagService {
    Boolean isExist(String name);
    Tag getByName(String name);
    Tag save(Tag tag);
    Tag getById(Long id);
}
