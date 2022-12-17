package com.example.backendhome.repository;

import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.enums.TypeGage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GageRepository extends JpaRepository<Gage, UUID> {

    @Query(value = """
            select g from Gage g
            left join fetch g.flat f
            """)
    List<Gage> findGagesWithFlat();

    Optional<Gage> findByTypeGageAndFlat(TypeGage typeGage, Flat flat);

    @Query(value = """
            select g from Gage g
            left join fetch g.flat f
            where f.id = :flatId
            """)
    List<Gage> findGagesWithFlatByFlatId(UUID flatId);
}
