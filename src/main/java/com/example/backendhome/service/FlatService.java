package com.example.backendhome.service;

import com.example.backendhome.entity.Flat;
import com.example.backendhome.repository.FlatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlatService {

    private final FlatRepository flatRepository;

    public List<Flat> getFlats() {
        return flatRepository.findAll();
    }

    public Flat getFlat(UUID id) {
        return flatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Flat not found by id : " + id));
    }

    @Transactional
    public Flat createFlat(Flat flat) {
        return flatRepository.save(flat);
    }

    @Transactional
    public void deleteFlat(UUID id) {
        flatRepository.deleteById(id);
    }
}
