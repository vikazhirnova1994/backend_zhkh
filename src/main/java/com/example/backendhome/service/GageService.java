package com.example.backendhome.service;

import com.example.backendhome.dto.request.GageRequestWithAddressDto;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.User;
import com.example.backendhome.entity.enums.TypeGage;
import com.example.backendhome.repository.GageRepository;
import com.example.backendhome.service.generator.AddressSplitter;
import com.example.backendhome.service.generator.DateTransform;
import com.example.backendhome.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GageService {
    private final GageRepository gageRepository;
    private final UserService userService;
    private final FlatService flatService;
    private final DateTransform dateTransform;
    private final AddressSplitter addressSplitter;

    public List<Gage> getGages() {
        return gageRepository.findGagesWithFlat();
    }

    public List<Gage> getUserGages() {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID flatId = user.getContract().getFlat().getId();
        log.info("Finding gages by id of users flat: {}", flatId);
        return gageRepository.findGagesWithFlatByFlatId(flatId)
                .stream()
                .filter(el -> el.getDisposalDate() == null)
                .collect(Collectors.toList());
    }

    public Gage getGage(UUID id) {
        log.info("Finding gage by id: {}", id);
        return gageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not found gage by id : " + id));
    }

    public Slice<Gage> getGagePage(int page, int size) {
        log.info("Fetching user gages data for page {} of size {}", page, size);
        Pageable of = PageRequest.of(page, size);
        return gageRepository.findGage(of);
    }

    public Gage getGageByTypeGageAndFlat(TypeGage typeGage, Flat flat) {
        log.info("Finding gage by type gage and flat: {}, {}", typeGage, flat);
        return gageRepository.findByTypeGageAndFlat(typeGage, flat.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not found gage by type gage and flat"));
    }

    @Transactional
    public Gage createGage(Gage newGage) {
        return gageRepository.save(newGage);
    }

    @Transactional
    public void deleteGage(UUID id) {
        log.info("Finding gage by id: {}", id);
        Gage gage = gageRepository.findById(id).orElseThrow();
        gage.setDisposalDate(LocalDate.now());
        log.info("Changing disposal date for gage: {}", gage);
        gageRepository.save(gage);
    }

    public List<TypeGage> getTypeGages() {
        log.info("Getting list of type gages...");
        List<TypeGage> typeGages = gageRepository.findAll()
                .stream()
                .map(Gage::getTypeGage)
                .distinct()
                .collect(Collectors.toList());
        log.info("List of type gages: {}", typeGages);

        return typeGages;
    }

    @Transactional
    public Gage createCage(GageRequestWithAddressDto dto) {
        log.info("Transforming dto...");
        log.info("Getting information about flat from dto...");
        String address = dto.getAddress();
        Flat flat = flatService.getFlat(
                addressSplitter.getCity(address),
                addressSplitter.getStreet(address),
                addressSplitter.getHouseNumber(address),
                addressSplitter.getEntrance(address),
                addressSplitter.getFlatNumber(address));

        log.info("Getting gage installation date from dto...");
        LocalDate installationDate = dateTransform.toTransform(dto.getInstallationDate());

        log.info("Creating new gage...");
        Gage newGage = Gage.builder()
                .serialNumber(dto.getSerialNumber())
                .typeGage(dto.getTypeGage())
                .manufacturer(dto.getManufacturer())
                .installationDate(installationDate)
                .flat(flat)
                .build();

        log.info("Saving new gage...");
        gageRepository.save(newGage);

        return newGage;
    }

    private String getValue(String arg) {
        return arg.split(" ")[1];
    }

    public int getCountTypeGage() {
        log.info("Getting count of type gages...");
        int size = gageRepository.findAll()
                .stream()
                .map(Gage::getTypeGage)
                .distinct()
                .collect(Collectors.toList())
                .size();
        log.info("Count of type gages: {}", size);

        return size;
    }
}
