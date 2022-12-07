package com.example.backendhome.controller;

import com.example.backendhome.entity.Gage;
import com.example.backendhome.service.GageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gage")
@CrossOrigin(origins = "http://localhost:4200")
public class GageController {

    private final GageService gageService;

    @GetMapping("/")
    public ResponseEntity<List<Gage>> getGages(){
        List<Gage> gages = gageService.getGages();
        return ResponseEntity.ok(gages);
    }
}