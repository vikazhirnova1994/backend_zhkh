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

    @GetMapping("/")
    public ResponseEntity<List<Flat>> getFlats(){
        return ResponseEntity.ok(flatService.getFlats());
    }

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

    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDto>> getTypeGages() {
        return ResponseEntity.ok(
                flatService.getTypeGages()
                        .stream()
                        .map(flatMapper::toAddressResponseDto)
                        .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public ResponseEntity<Flat> createFlat(@Valid @RequestBody FlatRequestDto flatDto){
        return ResponseEntity.ok(flatService.createFlat(
                flatMapper.toFlat(flatDto)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/edit")
    public ResponseEntity<FlatResponseDto> updateFlat(@PathVariable("id") String id,
                                                      @RequestBody FlatUpdateRequestDto flatDto) {
        return ResponseEntity.ok(
                flatMapper.toFlatResponseDto(
                        flatService.saveFlat(UUID.fromString(id), flatDto)));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteFlat(@PathVariable("id") String id){
        flatService.deleteFlat(UUID.fromString(id));
    }
}
