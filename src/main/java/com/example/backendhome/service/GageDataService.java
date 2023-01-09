package com.example.backendhome.service;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.GageData;
import com.example.backendhome.entity.User;
import com.example.backendhome.entity.enums.TypeGage;
import com.example.backendhome.repository.GageDataRepository;
import com.example.backendhome.service.generator.LocalDateGenerator;
import com.example.backendhome.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
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
    private final LocalDateGenerator localDateGenerator;

    public List<GageData> getGagesData() {
        return gageDataRepository.findAll();
    }

    public GageData getGageData(UUID id) {
        return gageDataRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GageData not found by id : " + id));
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

    public Slice<GageData> getUserGagesDataPage(String serialNumber, int page, int size) {
        log.info("Fetching user gages data for page {} of size {}", page, size);
        User user = userService.getUser(SecurityUtil.getUserId());
        Pageable of = PageRequest.of(page, size);
        return gageDataRepository.findGagesData(user.getId(), serialNumber, of);
    }


    public List<GageData> getUserGagesData(UUID gageId) {
        User user = userService.getUser(SecurityUtil.getUserId());

        return gageDataRepository.findGagesDataByUserIdAndGageId(user.getId(), gageId);
    }
    public List<GageData> getLastUserGagesData() {
        User user = userService.getUser(SecurityUtil.getUserId());

        UUID userId = user.getId();
        LocalDate from = localDateGenerator.from();
        LocalDate to = localDateGenerator.to();

        return gageDataRepository.findLastGagesDataByUserId(userId, from, to).stream()
                .sorted(Comparator.comparing(GageData::getDepartureDate).reversed())
                .limit(5)
                .toList();
    }

    public Boolean canAddUserGagesData() {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID userId = user.getId();

        LocalDate now = LocalDate.now();

        if (now.getDayOfMonth() >= 20 && now.getDayOfMonth() <= 25) {
            LocalDate from = LocalDate.of(now.getYear(), now.getMonth().getValue(), 20);
            LocalDate to = LocalDate.of(now.getYear(), now.getMonth().getValue(), 25);

            return gageDataRepository.findLastGagesDataByUserId(userId, from, to).stream()
                    .sorted(Comparator.comparing(GageData::getDepartureDate).reversed())
                    .limit(5)
                    .toList().size() == 0;
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createNewUserGagesData(String data, TypeGage typeGage) {
        User user = userService.getUser(SecurityUtil.getUserId());
        GageData gageData = createGageData(user, data, typeGage);
        gageDataRepository.save(gageData);
    }
    private GageData createGageData(User user, String data, TypeGage typeGage){
        Gage gage = gageService.getGageByTypeGageAndFlat(typeGage,
                user.getContract().getFlat());

        return GageData.builder()
                .user(user)
                .gage(gage)
                .data(data)
                .departureDate(LocalDate.now())
                .build();
    }
}

