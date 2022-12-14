package com.example.backendhome.service;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.GageData;
import com.example.backendhome.entity.User;
import com.example.backendhome.repository.GageDataRepository;
import com.example.backendhome.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GageDataService {

    private final GageDataRepository gageDataRepository;
    private final GageService gageService;
    private final UserService userService;

    public List<GageData> getGageDatas() {
        return gageDataRepository.findAll();
    }

    public GageData getGageData(UUID id) {
        return gageDataRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GageData not found by id : " + id));
    }

    @Transactional
    public GageData createGageData(GageData newGageData, GageDataRequestDto gageDataDto) {
        User user = userService.getUser(SecurityUtil.getUserId());

        Gage gage = gageService.getGageByTypeGageAndFlat(
                gageDataDto.getTypeGage(),
                user.getContract().getFlat());

        newGageData.setUser(user);
        newGageData.setGage(gage);
        newGageData.setDepartureDate(LocalDate.now());
        GageData saveGage = gageDataRepository.save(newGageData);

        return gageDataRepository.findGageDataWithGage(saveGage.getId())
                .orElseThrow(() -> new EntityNotFoundException(" " + saveGage.getId()));
    }

    @Transactional
    public void deleteGageData(UUID id) {
        gageDataRepository.deleteById(id);
    }
}
