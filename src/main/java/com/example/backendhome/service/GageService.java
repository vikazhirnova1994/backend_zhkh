package com.example.backendhome.service;

import com.example.backendhome.entity.Gage;
import com.example.backendhome.repository.GageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GageService {
    private final GageRepository gageRepository;

    public List<Gage> getGages(){
        return gageRepository.findAllWithFlat();
    }

}
