package com.example.backendhome.controller;


import com.example.backendhome.entity.Flat;
import com.example.backendhome.service.FlatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flat")
//@CrossOrigin(origins = "http://localhost:4200",
      //  methods= { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST} , maxAge = 36000)
      //  "GET,POST,PUT,DELETE,ORIGIN")
public class FlatController {

    private final FlatService flatService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<List<Flat>> getFlats(){
        List<Flat> flats = flatService.getFlats();
        return ResponseEntity.ok(flats);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlat(@PathVariable UUID id){
        flatService.deleteFlat(id);
        return ResponseEntity.ok("Successfuly deleted");
    }
}
