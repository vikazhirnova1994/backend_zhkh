package com.example.backendhome.controller;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.dto.request.NewUserGagesDataDto;
import com.example.backendhome.dto.response.GageDataResponseDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.entity.GageData;
import com.example.backendhome.entity.enums.TypeGage;
import com.example.backendhome.mapper.GageDataMapper;
import com.example.backendhome.service.GageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gage-data")
public class GageDataController {

    private final GageDataService gageDataService;
    private final GageDataMapper gageDataMapper;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<GageData> getGagesData() {
        return gageDataService.getGagesData();
    }

    @GetMapping("/user")
    public ResponseEntity<HttpResponse> getUserGagesDataPageable(
            @RequestParam Optional<String> serialNumber,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", gageDataService.getUserGagesDataPage(
                                        serialNumber.orElse(""),
                                        page.orElse(0),
                                        size.orElse(10))
                                .map(gageDataMapper::toGageDataResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/user/{gageId}")
    @ResponseStatus(HttpStatus.OK)
    public List<GageDataResponseDto> getUserGagesData(@PathVariable UUID gageId) {
        return gageDataService.getUserGagesData(gageId)
                .stream()
                .map(gageDataMapper::toGageDataResponseDto)
                .toList();
    }
    @GetMapping("/user/can-add")
    public Boolean canAddUserGagesData(){
        return gageDataService.canAddUserGagesData();
    }

    @GetMapping("/user/last")
    @ResponseStatus(HttpStatus.OK)
    public List<GageDataResponseDto> getLastUserGagesData() {
        return gageDataService.getLastUserGagesData()
                .stream()
                .map(gageDataMapper::toGageDataResponseDto)
                .toList();
    }

    @PostMapping("/user/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUserGagesData(@Valid @RequestBody NewUserGagesDataDto newUserGagesData) {
        gageDataService.createNewUserGagesData(newUserGagesData.getElectricityDay(), TypeGage.ELECTRICAL_ENERGY);
        gageDataService.createNewUserGagesData(newUserGagesData.getElectricityNight(), TypeGage.ELECTRICAL_ENERGY);
        gageDataService.createNewUserGagesData(newUserGagesData.getEnergy(), TypeGage.THERMAL_ENERGY);
        gageDataService.createNewUserGagesData(newUserGagesData.getWaterHot(), TypeGage.WATER);
        gageDataService.createNewUserGagesData(newUserGagesData.getWaterCool(), TypeGage.WATER);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public GageDataResponseDto createGageData(@Valid @RequestBody GageDataRequestDto gageDataDto) {
        return gageDataMapper.toGageDataResponseDto(
                gageDataService.createGageData(
                        gageDataMapper.toGageData(gageDataDto), gageDataDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteFlat(@PathVariable UUID id) {
        gageDataService.deleteGageData(id);
        return "Successfully deleted";
    }
}
