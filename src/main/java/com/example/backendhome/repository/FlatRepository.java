package com.example.backendhome.repository;

import com.example.backendhome.entity.Flat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlatRepository extends JpaRepository<Flat, UUID> {
    @Query(value = """
            select * from flats
            """,
            countQuery = """
                    select count(*)  from flats
                    """,
            nativeQuery = true)
    Slice<Flat> findFlat(Pageable pageable);
}
