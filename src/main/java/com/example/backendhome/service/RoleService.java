package com.example.backendhome.service;

import com.example.backendhome.entity.Role;
import com.example.backendhome.entity.enums.ERole;
import com.example.backendhome.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findRole(ERole eRole) {
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
