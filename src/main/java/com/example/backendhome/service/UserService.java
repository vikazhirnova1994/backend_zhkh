package com.example.backendhome.service;

import com.example.backendhome.dto.request.UserUpdateRequestDto;
import com.example.backendhome.entity.Contract;
import com.example.backendhome.entity.Role;
import com.example.backendhome.entity.User;
import com.example.backendhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final ContractService contractService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public Boolean isExistUser(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("" + id));
    }

    public Slice<User> getUsers(UUID userId, int page, int size) {

        log.info("Fetching user for page {} of size {}", page, size);
        Pageable of = PageRequest.of(page, size);
        return userRepository.findUsers(userId, of);
    }

    @Transactional
    public void createUser(User newUser, String roleName, String contractNumber) {
        Contract contract = contractService.findContractWithFlat(contractNumber);

        Role role = roleService.getRoles().stream()
                .filter(element -> element.getName().name().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("" + roleName));

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);
        newUser.setContract(contract);
        userRepository.save(newUser);
    }

    @Transactional
    public User saveUser(UUID id, UserUpdateRequestDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
        if (!userDto.getPassword().equals(null)) {
            user.setPassword(encoder.encode(userDto.getPassword()));
        }
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void save(User newUser) {
        userRepository.save(newUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
