package com.example.backendhome.repository;

import com.example.backendhome.entity.GageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GageDataRepository  extends JpaRepository<GageData, UUID> {
}
