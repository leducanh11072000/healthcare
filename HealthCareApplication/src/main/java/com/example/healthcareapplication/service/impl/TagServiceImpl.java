package com.example.healthcareapplication.service.impl;

import com.example.healthcareapplication.model.Tag;
import com.example.healthcareapplication.repository.TagRepository;
import com.example.healthcareapplication.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private final TagRepository tagRepository;
    @Override
    public Boolean isExist(String name) {
        Tag tag = tagRepository.getAllByNameEqualsIgnoreCase(name);
        if (tag == null) {
            return false;
        }
        return true;
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.getAllByNameEqualsIgnoreCase(name);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
}
