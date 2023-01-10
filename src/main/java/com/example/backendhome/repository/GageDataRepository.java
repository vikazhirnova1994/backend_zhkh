package com.example.backendhome.repository;

import com.example.backendhome.entity.GageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GageDataRepository extends JpaRepository<GageData, UUID> {

    @Query(value = """
            SELECT gd FROM GageData gd
            JOIN FETCH gd.gage g
            where gd.id = :id
            """)
    Optional<GageData> findGageDataWithGage(UUID id);

    @Query(value = """
            SELECT gd FROM GageData gd
            JOIN FETCH gd.gage g
            JOIN FETCH gd.user u
            where u.id = :userId
            and gd.departureDate between :from and :to and g.disposalDate is null
            """)
    List<GageData> findLastGagesDataByUserId(UUID userId, LocalDate from, LocalDate to);

    @Query(value = """
            SELECT gd FROM GageData gd
            JOIN FETCH gd.gage g
            JOIN FETCH gd.user u
            where u.id = :userId and g.id = :gageId
            """)
    List<GageData> findGagesDataByUserIdAndGageId(UUID userId, UUID gageId);

    @Query(value =
           """
           select * from gages_data
           left join db_users du on du.id = gages_data.user_id
           left join gages g on gages_data.gage_id = g.id
           where du.id = ?1 and g.serial_number like %?2%
           """,
            countQuery = """
            select count(*)  from gages_data
            left join db_users du on du.id = gages_data.user_id
            left join gages g on gages_data.gage_id = g.id
            where du.id = ?1 and g.serial_number like %?2%
            """,
            nativeQuery = true)
    Page<GageData> findGagesData(UUID userId, String serialNumber, Pageable pageable);
}
