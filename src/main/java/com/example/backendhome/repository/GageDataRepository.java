package com.example.backendhome.repository;

import com.example.backendhome.entity.Contract;
import com.example.backendhome.entity.GageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GageDataRepository  extends JpaRepository<GageData, UUID> {

    @Query(value = """
            SELECT gd FROM GageData gd
            JOIN FETCH gd.gage g
            where gd.id = :id
            """)
    Optional<GageData> findGageDataWithGage( UUID id);
}
