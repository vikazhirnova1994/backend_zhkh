package com.example.backendhome.service;

import com.example.backendhome.dto.request.FlatUpdateRequestDto;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.repository.FlatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlatService {
    private final FlatRepository flatRepository;

    public List<Flat> getFlats() {
        return flatRepository.findAll();
    }

    public Flat getFlat(String city, String street, String houseNumber, Integer entrance, Integer flatNumber) {
        return flatRepository.findByCityAndStreetAndHouseNumberAndEntranceAndFlatNumber(
                        city, street, houseNumber, entrance, flatNumber)
                .orElseThrow(() -> new EntityNotFoundException("Flat not found"));
    }

    public Flat getFlat(UUID id) {
        return flatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Flat not found by id : " + id));
    }

    public Slice<Flat> getFlatsPage(int page, int size) {
        Pageable of = PageRequest.of(page, size);
        return flatRepository.findFlat(of);
    }

    public List<String> getAddresses() {
        return flatRepository.findAll()
                .stream()
                .map(Flat::toString)
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public Flat saveFlat(UUID id, FlatUpdateRequestDto flatDto) {
        Flat flat = flatRepository.findById(id).orElseThrow();
        flat.setCity(flatDto.getCity());
        flat.setStreet(flatDto.getStreet());
        flat.setHouseNumber(flatDto.getHouseNumber());
        flat.setEntrance(Integer.valueOf(flatDto.getEntrance()));
        flat.setFlatNumber(Integer.valueOf(flatDto.getFlatNumber()));
        flatRepository.save(flat);
        return flat;
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
