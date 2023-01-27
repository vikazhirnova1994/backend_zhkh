package com.example.backendhome.service;

import com.example.backendhome.dto.request.ClaimUpdateRequestDto;
import com.example.backendhome.dto.request.NewUserClaimDto;
import com.example.backendhome.entity.Claim;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.User;
import com.example.backendhome.entity.enums.ClaimStatus;
import com.example.backendhome.repository.ClaimRepository;
import com.example.backendhome.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final UserService userService;

    public List<String[]> getClaims() {
        List<Claim> claims = claimRepository.findClaims();
        List<String[]> obj = new ArrayList<>();

        AtomicInteger count = new AtomicInteger(0);

        claims.stream().peek(element -> {
            String[] strings = new String[7];
            int i = count.incrementAndGet();
            strings[0] = String.valueOf(i);
            strings[1] = element.getUser().getContract().getContractNumber();
            strings[2] = element.getUser().getContract().getFlat().toString();
            strings[3] = element.getDescription();
            strings[4] = element.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            strings[5] = element.getStatus().name();
            strings[6] = element.getExecutorIdentificationNumber();
            obj.add(strings);
        }  ).forEach(el -> log.info(" {}", el) );

        return obj;
    }

    public Slice<Claim> getUserClaimsPage(int page, int size) {
        log.info("Fetching user gages data for page {} of size {}", page, size);
        User user = userService.getUser(SecurityUtil.getUserId());
        Flat flat = user.getContract().getFlat();
        UUID flatId = flat.getId();
        Pageable of = PageRequest.of(page, size);
        //return claimRepository.findUserClaims(user.getId(), of);
        return claimRepository.findUserClaimsByFlatId(flatId, of);
    }

    public Slice<Claim> getClaimsPage(String status, int page, int size) {
        log.info("Fetching user gages data for page {} of size {}", page, size);
        Pageable of = PageRequest.of(page, size);
        return claimRepository.findClaims(status, of);
    }

    @Transactional
    public void createNewUserClaimsData(NewUserClaimDto dto) {
        User user = userService.getUser(SecurityUtil.getUserId());
        Claim newClaim = Claim.builder()
                .description(dto.getDescription())
                .creationDate(LocalDate.now())
                .user(user)
                .status(ClaimStatus.ACTIVE)
                .build();
        claimRepository.save(newClaim);
    }

    @Transactional
    public Claim updateClaimExecutor(UUID id, ClaimUpdateRequestDto claimDto) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setExecutorIdentificationNumber(claimDto.getExecutorIdentificationNumber());
        claim.setStatus(ClaimStatus.IN_PROGRESS);
        claimRepository.save(claim);
        return claim;
    }

    @Transactional
    public void deleteClaim(UUID id) {
        log.info("Finding gage by id: {}", id);
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setCompletionDate(LocalDate.now());
        log.info("Changing disposal date for gage: {}", claim);
        claim.setStatus(ClaimStatus.COMPLITED);
        claimRepository.save(claim);
    }
}
