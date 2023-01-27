package com.example.backendhome.service;

import com.example.backendhome.entity.Contract;
import com.example.backendhome.entity.User;
import com.example.backendhome.util.exception.UserExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterService {
  private final UserService userService;
  private final ContractService contractService;
  private final RoleCreater roleCreater;

  @Transactional
  public void registerUser(User newUser, String contractNumber) {

    if (userService.isExistUser(newUser.getUsername())) {
      throw new UserExistException("Error: Username is exist");
    }
    Contract contract = contractService.findContract(contractNumber);
    newUser.setRoles(roleCreater.getRole(contractNumber));
    newUser.setContract(contract);
    userService.save(newUser);
  }
}
