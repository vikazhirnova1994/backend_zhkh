package com.example.backendhome.service;

import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.User;
import com.example.backendhome.entity.enums.TypeGage;
import com.example.backendhome.repository.GageRepository;
import com.example.backendhome.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GageService {

    private final GageRepository gageRepository;
    private final UserService userService;

    public List<Gage> getGages() {
        return gageRepository.findGagesWithFlat();
    }

    public List<Gage> getUserGages() {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID flatId = user.getContract().getFlat().getId();
        return gageRepository.findGagesWithFlatByFlatId(flatId);
    }


    public Gage getGage(UUID id) {
        return gageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gage not found by id : " + id));
    }

    @Transactional
    public Gage createGage(Gage newGage) {
        return gageRepository.save(newGage);
    }

    public Gage getGageByTypeGageAndFlat(TypeGage typeGage, Flat flat) {
       return gageRepository.findByTypeGageAndFlat(typeGage, flat)
               .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Transactional
    public void deleteGage(UUID id) {
        gageRepository.deleteById(id);
    }
}
