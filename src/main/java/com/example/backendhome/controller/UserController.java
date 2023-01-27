package com.example.backendhome.controller;

import com.example.backendhome.dto.request.UserRequestDto;
import com.example.backendhome.dto.request.UserUpdateRequestDto;
import com.example.backendhome.dto.response.HttpResponse;
import com.example.backendhome.dto.response.UserResponseDto;
import com.example.backendhome.entity.User;
import com.example.backendhome.mapper.UserMapper;
import com.example.backendhome.service.ContractService;
import com.example.backendhome.service.RoleService;
import com.example.backendhome.service.UserService;
import com.example.backendhome.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final ContractService contractService;
    private final UserMapper userMapper;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpResponse> getUsersPageable(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {
        User user = userService.getUser(SecurityUtil.getUserId());
        UUID userId = user.getId();
        log.info("userId : {} ...", userId);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", userService.getUsers(userId,
                                        page.orElse(0),
                                        size.orElse(10))
                                .map(userMapper::toUserResponseDto)))
                        .message("")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PreAuthorize("hasRole('USER') or hasRole('DISPATCHER') or hasRole('ADMIN')")
    @GetMapping("/roles")
    public List<String> getRoleName() {
        return roleService.getRoles().stream().map(el -> el.getName().name()).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER') or hasRole('DISPATCHER') or hasRole('ADMIN')")
    @GetMapping("/contractNumber")
    public List<String> getContractNumbers() {
        return contractService.getContracts().stream().map(el -> el.getContractNumber()).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userMapper.toUser(userRequestDto),
                userRequestDto.getRole(),
                userRequestDto.getContractNumber());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/edit")
    public ResponseEntity<UserResponseDto> updateFlat(@PathVariable("id") String id,
                                                      @Valid @RequestBody UserUpdateRequestDto userDto) {
        return ResponseEntity.ok(
                userMapper.toUserResponseDto(
                        userService.saveUser(UUID.fromString(id), userDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteFlat(@PathVariable("id") String id){
        userService.deleteUser(UUID.fromString(id));
    }
}
