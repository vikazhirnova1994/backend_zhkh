package com.example.backendhome.controller;

import com.example.backendhome.dto.request.GageRequestDto;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.mapper.GageMapper;
import com.example.backendhome.service.GageService;
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
@RequestMapping("/api/gage")
@CrossOrigin(origins = "http://localhost:4200")
public class GageController {

    private final GageService gageService;
    private final GageMapper gageMapper;

    @GetMapping("/")
    public ResponseEntity<List<Gage>> getGages(){
        return ResponseEntity.ok(gageService.getGages());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<Gage> createFlat(@Valid GageRequestDto gageDto){
        return ResponseEntity.ok(
                gageService.createGage(
                        gageMapper.toGage(gageDto)));
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{id}")
    public ResponseEntity<Gage> getFlat(@PathVariable UUID id){
        return ResponseEntity.ok(gageService.getGage(id));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlat(@PathVariable UUID id){
        gageService.deleteGage(id);
        return ResponseEntity.ok("Successfuly deleted");
    }
}