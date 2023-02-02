package com.example.backendhome.controller;

import com.example.backendhome.dto.request.GageRequestWithAddressDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.dto.response.TypeGageResponseDto;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.mapper.GageMapper;
import com.example.backendhome.service.GageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class GageController {
    private final GageService gageService;
    private final GageMapper gageMapper;

    @PreAuthorize("hasRole('USER') or hasRole('DISPATCHER') or hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Gage>> getGages() {
        return ResponseEntity.ok(gageService.getGages());
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-gages")
    public ResponseEntity<List<Gage>> getUserGages() {
        return ResponseEntity.ok(gageService.getUserGages());
    }

    @PreAuthorize("hasRole('USER') or hasRole('DISPATCHER') or hasRole('ADMIN')")
    @GetMapping("/type-gages")
    public ResponseEntity<List<TypeGageResponseDto>> getTypeGages() {
        return ResponseEntity.ok(
                gageService.getTypeGages().stream()
                        .map(gageMapper::toTypeGageResponseDto)
                        .collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public ResponseEntity<Gage> createGage(@Valid @RequestBody GageRequestWithAddressDto dto) {
        return ResponseEntity.ok(gageService.createCage(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteGage(@PathVariable("id") String id) {
        gageService.deleteGage(UUID.fromString(id));
    }
}