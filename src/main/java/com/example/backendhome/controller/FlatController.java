package com.example.backendhome.controller;


import com.example.backendhome.dto.request.FlatRequestDto;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.mapper.FlatMapper;
import com.example.backendhome.service.FlatService;
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
@RequestMapping("/api/flat")
public class FlatController {

    private final FlatService flatService;
    private final FlatMapper flatMapper;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<List<Flat>> getFlats(){
        return ResponseEntity.ok(flatService.getFlats());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<Flat> createFlat(@Valid FlatRequestDto flatDto){
        return ResponseEntity.ok(flatService.createFlat(
                flatMapper.toFlat(flatDto)));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{id}")
    public ResponseEntity<Flat> getFlat(@PathVariable UUID id){
        return ResponseEntity.ok(flatService.getFlat(id));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlat(@PathVariable UUID id){
        flatService.deleteFlat(id);
        return ResponseEntity.ok("Successfuly deleted");
    }
}