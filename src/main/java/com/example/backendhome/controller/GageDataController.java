package com.example.backendhome.controller;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.dto.response.GageDataResponseDto;
import com.example.backendhome.entity.GageData;
import com.example.backendhome.mapper.GageDataMapper;
import com.example.backendhome.service.GageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gage-data")
@CrossOrigin(origins = "http://localhost:4200")
public class GageDataController {

    private final GageDataService gageDataService;
    private final GageDataMapper gageDataMapper;


    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<GageData> getGages() {
        return gageDataService.getGageDatas();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public GageDataResponseDto createFlat(@Valid @RequestBody GageDataRequestDto gageDataDto) {
        return gageDataMapper.toGageDataResponseDto(
                        gageDataService.createGageData(
                                gageDataMapper.toGageData(gageDataDto), gageDataDto));
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GageData getFlat(@PathVariable UUID id) {
        return gageDataService.getGageData(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteFlat(@PathVariable UUID id) {
        gageDataService.deleteGageData(id);
        return "Successfuly deleted";
    }
}
