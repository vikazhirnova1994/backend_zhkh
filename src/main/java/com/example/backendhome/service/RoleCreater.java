package com.example.backendhome.service;

import com.example.backendhome.entity.ERole;
import com.example.backendhome.entity.Role;
import com.example.backendhome.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleCreater {

    private final RoleRepository roleRepository;

    private final static String CONTRACT_FOR_ADMIN = "00000000000000";
    private final static String CONTRACT_FOR_MODERATOR = "10000000000000";
    private final static String CONTRACT_FOR_DISPATCHER = "20000000000000";

    @Transactional
    public Set<Role> getRole(String contractNumber) {
        Set<Role> roles = new HashSet<>();
        ERole eRole = checkContractNumber(contractNumber);
        Role userRole = roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
        roles.add(userRole);
       return roles;
    }

    private ERole checkContractNumber(String contractNumber){
        if (contractNumber.equals(CONTRACT_FOR_ADMIN))
            return ERole.ADMIN;
        if (contractNumber.equals(CONTRACT_FOR_MODERATOR))
            return ERole.MODERATOR;
        if (contractNumber.equals(CONTRACT_FOR_DISPATCHER))
            return ERole.ADMIN;
        return ERole.USER;
    }
}
