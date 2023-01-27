package com.example.backendhome.controller;

import com.example.backendhome.dto.request.ClaimUpdateRequestDto;
import com.example.backendhome.dto.request.NewUserClaimDto;
import com.example.backendhome.dto.response.ClaimResponseDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.mapper.ClaimMapper;
import com.example.backendhome.service.ClaimService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/claim")
public class ClaimController {
    private final ClaimService claimService;
    private final ClaimMapper claimMapper;

    @PreAuthorize("hasRole('USER') or hasRole('DISPATCHER')")
    @GetMapping("")
    public ResponseEntity<HttpResponse> getClaimsPageable(
            @RequestParam Optional<String> status,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", claimService.getClaimsPage(
                                        status.orElse(""),
                                        page.orElse(0),
                                        size.orElse(10))
                                .map(claimMapper::toClaimResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PreAuthorize("hasRole('DISPATCHER')")
    @GetMapping("/excel")
    public List<String[]> getClaimsForExcel(){
        return claimService.getClaims();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<HttpResponse> getUserClaimsPageable(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", claimService.getUserClaimsPage(
                                        page.orElse(0),
                                        size.orElse(5))
                                .map(claimMapper::toUserClaimResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUserClaimsData(@Valid @RequestBody NewUserClaimDto newUserClaimData) {
        claimService.createNewUserClaimsData(newUserClaimData);
    }

    @PreAuthorize("hasRole('DISPATCHER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/edit")
    public ResponseEntity<ClaimResponseDto> updateClaimData(@PathVariable("id") String id,
                                                            @Valid @RequestBody ClaimUpdateRequestDto claimDto) {
        return ResponseEntity.ok(
                claimMapper.toClaimResponseDto(
                        claimService.updateClaimExecutor(UUID.fromString(id), claimDto)));
    }

    @PreAuthorize("hasRole('DISPATCHER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteClaim(@PathVariable("id") String id){
        claimService.deleteClaim(UUID.fromString(id));
    }
}
