package com.example.backendhome.controller;

import com.example.backendhome.dto.request.UserRequestDto;
import com.example.backendhome.entity.User;
import com.example.backendhome.mapper.UserMapper;
import com.example.backendhome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

   // @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

   // @CrossOrigin(origins = "http://localhost:4200")
   /* @PostMapping("/")
    public ResponseEntity<User> createFlat(@Valid UserRequestDto userDto){
        return ResponseEntity.ok(userService.createUser(
                userMapper.toUser(userDto), userDto.getRole()));
    }*/

  //  @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{id}")
    public ResponseEntity<User> getFlat(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUser(id));
    }

  //  @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlat(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Successfuly deleted");
    }

}
