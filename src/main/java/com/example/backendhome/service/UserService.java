package com.example.backendhome.service;

import com.example.backendhome.entity.Role;
import com.example.backendhome.entity.User;
import com.example.backendhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;

    public Boolean isExistUser(String username) {
       return userRepository.existsByUsername(username);
    }

    public void save(User newUser ) {
       userRepository.save(newUser);
    }

    public User createUser(User newUser, String roleName) {

        Role role = roleService.getRoles().stream()
                .filter(element -> element.getName().toString().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("" + roleName));

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("" + id));
    }

    public List<User> getUsers() {
        return userRepository.findAllUsers();
    }

    public void deleteUser(UUID id) {
    }
}
