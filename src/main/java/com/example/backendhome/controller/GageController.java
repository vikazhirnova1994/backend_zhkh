package com.example.backendhome.controller;

import com.example.backendhome.dto.request.FlatRequestDto;
import com.example.backendhome.dto.request.GageRequestDto;
import com.example.backendhome.dto.request.GageRequestWithAddressDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.dto.response.TypeGageResponseDto;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.enums.TypeGage;
import com.example.backendhome.mapper.GageMapper;
import com.example.backendhome.service.GageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gage")
@CrossOrigin(origins = "http://localhost:4200")
public class GageController {

    private final GageService gageService;
    private final GageMapper gageMapper;

    @GetMapping("/")
    public ResponseEntity<List<Gage>> getGages() {
        return ResponseEntity.ok(gageService.getGages());
    }

    @GetMapping("")
    public ResponseEntity<HttpResponse> getGagesPageable(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", gageService.getGagePage(
                                        page.orElse(0),
                                        size.orElse(5))
                                .map(gageMapper::toGageResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());


    }

    @GetMapping("/user-gages")
    public ResponseEntity<List<Gage>> getUserGages() {
        return ResponseEntity.ok(gageService.getUserGages());
    }

    @GetMapping("/type-gages")
    public ResponseEntity<List<TypeGageResponseDto>> getTypeGages() {
        return ResponseEntity.ok(
                gageService.getTypeGages().stream().map(gageMapper::toTypeGageResponseDto).collect(Collectors.toList()));
    }

    @PostMapping("/")
    public ResponseEntity<Gage> createFlat(@Valid GageRequestDto gageDto) {
        return ResponseEntity.ok(
                gageService.createGage(
                        gageMapper.toGage(gageDto)));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public ResponseEntity<Gage> createFlat(@Valid @RequestBody GageRequestWithAddressDto dto){
        return ResponseEntity.ok(gageService.createCage(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Gage> getFlat(@PathVariable UUID id) {
        return ResponseEntity.ok(gageService.getGage(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteFlat(@PathVariable("id") String id){
        gageService.deleteGage(UUID.fromString(id));
    }
}