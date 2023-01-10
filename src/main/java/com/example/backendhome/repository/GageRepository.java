package com.example.backendhome.repository;

import com.example.backendhome.entity.Flat;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.enums.TypeGage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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


    @Query(value = """
            select g from Gage g
            left join fetch g.flat f
            where f.id = :flatId and g.typeGage = :typeGage and g.disposalDate is null
            """)
    Optional<Gage> findByTypeGageAndFlat(TypeGage typeGage, UUID flatId);

    @Query(value = """
            select g from Gage g
            left join fetch g.flat f
            where f.id = :flatId
            """)
    List<Gage> findGagesWithFlatByFlatId(UUID flatId);

    @Query(value = """
            select * from gages
            left join flats on gages.flat_id = flats.id
            where gages.disposal_date is null
            """,
            countQuery = """
            select count(*) from gages
            left join flats on gages.flat_id = flats.id
            where gages.disposal_date is null
            """,
            nativeQuery = true)
    Page<Gage> findGage(Pageable pageable);
}
