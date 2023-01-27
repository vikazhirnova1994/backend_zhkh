package com.example.backendhome.service;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.entity.Flat;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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

    public Slice<GageData> getUserGagesDataPage(String serialNumber, int page, int size) {
        log.info("Fetching user gages data for page {} of size {}", page, size);
        User user = userService.getUser(SecurityUtil.getUserId());
        Flat flat = user.getContract().getFlat();
        UUID flatId = flat.getId();
        Pageable of = PageRequest.of(page, size);
       return gageDataRepository.findFlatGagesData(flatId, serialNumber, of);
    }

    public Slice<GageData> getGagesDataPage(String contractNumber, String serialNumber, int page, int size) {
        log.info("Fetching user gages data for page {} of size {}", page, size);
        User user = userService.getUser(SecurityUtil.getUserId());
        Pageable of = PageRequest.of(page, size);
        return gageDataRepository.findGagesData(contractNumber, serialNumber, of);
    }

    public List<GageData> getUserGagesData(UUID gageId) {
        User user = userService.getUser(SecurityUtil.getUserId());
        log.info("Finding gages data by userId {} and by gageId {} ...", user.getId(), gageId);
        Flat flat = user.getContract().getFlat();
        UUID flatId = flat.getId();
        return gageDataRepository.findGagesDataByFlatIdAndGageId(flatId, gageId);
    }
    public List<GageData> getLastUserGagesData() {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID userId = user.getId();
        LocalDate from = null;
        LocalDate to = null;
        log.info("Generating date of from and to ...");
        if (canAddUserGagesData()) {
            from = localDateGenerator.fromPreviousMonth();
            to = localDateGenerator.toPreviousMonth();
        } else {
            from = localDateGenerator.fromCurrentMonth();
            to = localDateGenerator.toCurrentMonth();
        }
        log.info("from: {}", from);
        log.info("to: {}", to);
        log.info("Finding gages data by date of from {} and to {} ...", from, to);

        Flat flat = user.getContract().getFlat();
        UUID flatId = flat.getId();

        return gageDataRepository.findLastGagesDataByFlatId(flatId, from, to).stream()
                .sorted(Comparator.comparing(GageData::getDepartureDate).reversed())
                .limit(5)
                .toList();
    }

    public Boolean canAddUserGagesData() {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID userId = user.getId();
        LocalDate now = LocalDate.now();
        LocalDate from = null;
        LocalDate to = null;
        log.info("Generating date of from and to ...");
        if (now.getDayOfMonth() >= localDateGenerator.getDateFrom()
                && now.getDayOfMonth() <= localDateGenerator.getDateTo()) {
            from = localDateGenerator.fromCurrentMonth();
            log.info("from: {}", from);
            to = localDateGenerator.toCurrentMonth();
            log.info("to: {}", to);
            log.info("Finding last gages data by date of from {} and to {} ...", from, to);

            return gageDataRepository.findLastGagesDataByUserId(userId, from, to).stream()
                    .sorted(Comparator.comparing(GageData::getDepartureDate).reversed())
                    .limit(gageService.getCountTypeGage())
                    .toList()
                    .size() == 0;
        }
        return false;
    }

    @Transactional
    public GageData createGageData(GageData newGageData, GageDataRequestDto gageDataDto) {
        User user = userService.getUser(SecurityUtil.getUserId());
        TypeGage typeGage = gageDataDto.getTypeGage();
        Flat userFlat = user.getContract().getFlat();
        log.info("Getting gage by type of gage {} and users flat {} ...", typeGage, userFlat);
        Gage gage = gageService.getGageByTypeGageAndFlat(typeGage, userFlat);

        log.info("Creating new gage data ....");
        newGageData.setUser(user);
        newGageData.setGage(gage);
        newGageData.setDepartureDate(LocalDate.now());

        log.info("Saving new gage data ...");
        GageData saveGage = gageDataRepository.save(newGageData);

        return gageDataRepository.findGageDataWithGage(saveGage.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Error: can not saving gage data for" + saveGage.getId()));
    }

    @Transactional
    public void deleteGageData(UUID id) {
        log.info("Deleting gage data by id {}", id);
        gageDataRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createNewUserGagesData(String data, TypeGage typeGage) {
        log.info("Creating new gaga data for user by data {}, typeGage {} ....", data, typeGage);
        User user = userService.getUser(SecurityUtil.getUserId());
        GageData gageData = createGageData(user, data, typeGage);
        log.info("Saving new gaga data....");
        gageDataRepository.save(gageData);
    }
    private GageData createGageData(User user, String data, TypeGage typeGage){
        Gage gage = gageService.getGageByTypeGageAndFlat(typeGage, user.getContract().getFlat());
        log.info("Creating new gaga data, using builder....");

        return GageData.builder()
                .user(user)
                .gage(gage)
                .data(data)
                .departureDate(LocalDate.now())
                .build();
    }

    public List<String[]> getGageDataForExcel() {
        List<GageData> gagesData = gageDataRepository.findGagesData();
        List<String[]> obj = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);

        gagesData.stream().peek(element -> {
            String[] strings = new String[8];
            int i = count.incrementAndGet();
            strings[0] = String.valueOf(i);
            strings[1] = element.getUser().getUsername();
            strings[2] = element.getUser().getContract().getContractNumber();
            strings[3] = element.getUser().getContract().getFlat().toString();
            strings[4] = element.getGage().getSerialNumber();
            strings[5] = element.getGage().getTypeGage().name();
            strings[6] = element.getData();
            strings[7] = element.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            obj.add(strings);
        }  ).forEach(el -> log.info(" {}", el) );

        return obj;
    }
}

