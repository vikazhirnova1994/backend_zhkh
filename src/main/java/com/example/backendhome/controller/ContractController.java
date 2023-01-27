package com.example.backendhome.controller;

import com.example.backendhome.dto.request.ContractRequestDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.mapper.ContractMapper;
import com.example.backendhome.service.ContractService;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contract")
public class ContractController {
    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<HttpResponse> getContractsPageable(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", contractService.getContractsPage(
                                        page.orElse(0),
                                        size.orElse(5))
                                .map(contractMapper::toContractResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void createContract(@Valid @RequestBody ContractRequestDto contractRequestDto) {
        contractService.createContract(contractRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable("id") String id) {
        contractService.deleteContract(UUID.fromString(id));
    }
}
