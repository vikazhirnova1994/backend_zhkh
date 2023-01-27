package com.example.backendhome.controller;

import com.example.backendhome.dto.request.FlatRequestDto;
import com.example.backendhome.dto.request.FlatUpdateRequestDto;
import com.example.backendhome.dto.response.AddressResponseDto;
import com.example.backendhome.dto.response.FlatResponseDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.mapper.FlatMapper;
import com.example.backendhome.service.FlatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/flat")
public class FlatController {
    private final FlatService flatService;
    private final FlatMapper flatMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Flat>> getFlats(){
        return ResponseEntity.ok(flatService.getFlats());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<HttpResponse> getFlatsPageable(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", flatService.getFlatsPage(
                                        page.orElse(0),
                                        size.orElse(5))
                                .map(flatMapper::toFlatResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDto>> getAddresses() {
        return ResponseEntity.ok(
                flatService.getAddresses()
                        .stream()
                        .map(flatMapper::toAddressResponseDto)
                        .collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public ResponseEntity<Flat> createFlat(@Valid @RequestBody FlatRequestDto flatDto){
        return ResponseEntity.ok(flatService.createFlat(
                flatMapper.toFlat(flatDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/edit")
    public ResponseEntity<FlatResponseDto> updateFlat(@PathVariable("id") String id,
                                                      @Valid @RequestBody FlatUpdateRequestDto flatDto) {
        return ResponseEntity.ok(
                flatMapper.toFlatResponseDto(
                        flatService.saveFlat(UUID.fromString(id), flatDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteFlat(@PathVariable("id") String id){
        flatService.deleteFlat(UUID.fromString(id));
    }
}
