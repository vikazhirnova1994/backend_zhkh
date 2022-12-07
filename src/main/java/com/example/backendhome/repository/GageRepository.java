package com.example.backendhome.repository;

import com.example.backendhome.entity.Gage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GageRepository extends JpaRepository<Gage, UUID> {

    @Query(value = """
    select g from Gage g
    left join fetch g.flat f
    """)
    List<Gage> findAllWithFlat();
}
