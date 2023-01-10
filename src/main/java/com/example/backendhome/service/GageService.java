package com.example.backendhome.service;

import com.example.backendhome.dto.request.GageRequestWithAddressDto;
import com.example.backendhome.dto.request.InstallationDate;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.User;
import com.example.backendhome.entity.enums.TypeGage;
import com.example.backendhome.repository.GageRepository;
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
import java.util.Optional;
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

    public List<Gage> getGages() {
        return gageRepository.findGagesWithFlat();
    }

    public List<Gage> getUserGages() {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID flatId = user.getContract().getFlat().getId();
        return gageRepository.findGagesWithFlatByFlatId(flatId)
                .stream()
                .filter(el -> el.getDisposalDate() == null)
                .collect(Collectors.toList());
    }

    public Gage getGage(UUID id) {
        return gageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gage not found by id : " + id));
    }

    public Slice<Gage> getGagePage(int page, int size){
        log.info("Fetching user gages data for page {} of size {}", page, size);
        Pageable of = PageRequest.of(page, size);
        return gageRepository.findGage(of);
    }

    public Gage getGageByTypeGageAndFlat(TypeGage typeGage, Flat flat) {
        return gageRepository.findByTypeGageAndFlat(typeGage, flat)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Transactional
    public Gage createGage(Gage newGage) {
        return gageRepository.save(newGage);
    }

    @Transactional
    public void deleteGage(UUID id) {
        Gage gage = gageRepository.findById(id).orElseThrow();
        gage.setDisposalDate(LocalDate.now());
        gageRepository.save(gage);
    }

    public List<TypeGage> getTypeGages() {
        return gageRepository.findAll()
                .stream()
                .map(Gage::getTypeGage)
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public Gage createCage(GageRequestWithAddressDto dto) {

        String address = dto.getAddress();
        String[] split = address.split(", ");

        String city = split[0];
        String street = getValue(split[1]);
        String houseNumber = getValue(split[2]);
        Integer entrance =  Integer.valueOf(getValue(split[3]));
        Integer flatNumber =  Integer.valueOf(getValue(split[4]));

        Flat flat = flatService.getFlat(city, street, houseNumber, entrance, flatNumber);


        InstallationDate installationDateDto = dto.getInstallationDate();

        LocalDate installationDate = LocalDate.of(Integer.valueOf(installationDateDto.getYear()),
                Integer.valueOf(installationDateDto.getMonth()),
                Integer.valueOf(installationDateDto.getDay()));


        Gage newGage = Gage.builder()
                .serialNumber(dto.getSerialNumber())
                .typeGage(dto.getTypeGage())
                .manufacturer(dto.getManufacturer())
                .installationDate(installationDate)
                .flat(flat)
                .build();

         gageRepository.save(newGage);
        return newGage;
    }
    private String getValue(String arg){
       return arg.split(" ")[1];
    }

}
