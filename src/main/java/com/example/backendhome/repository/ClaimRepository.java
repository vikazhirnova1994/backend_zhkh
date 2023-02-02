package com.example.backendhome.repository;

import com.example.backendhome.entity.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    @Query(value = """
            select * from claims
            left join db_users du on du.id = claims.user_id
            where du.id = ?1
            """, countQuery = """
            select count(*)  from claims
            left join db_users du on du.id = claims.user_id
            where du.id = ?1
            """, nativeQuery = true)
    Page<Claim> findUserClaims(UUID userId, Pageable pageable);

    @Query(value = """
            select * from claims
            left join db_users du on du.id = claims.user_id
            left join contract c on c.id = du.contract_id
            left join flats f on f.id = c.flat_id
            where claims.status like %?1%
            """, countQuery = """
            select count(*)  from claims
            left join db_users du on du.id = claims.user_id
            left join contract c on c.id = du.contract_id
            left join flats f on f.id = c.flat_id
            where claims.status like %?1%
            """, nativeQuery = true)
    Page<Claim> findClaims(String status, Pageable pageable);

    @Query(value = """
            select cs from Claim cs
            join fetch cs.user u
            join fetch u.contract c
            join fetch c.flat f
             """)
    List<Claim> findClaims();

    @Query(value = """
            select * from claims
            left join db_users du on du.id = claims.user_id
            left join contract c on c.id = du.contract_id
            left join flats f on f.id = c.flat_id
            where f.id = ?1
            """, countQuery = """
            select count(*)  from claims
            left join db_users du on du.id = claims.user_id
            left join contract c on c.id = du.contract_id
            left join flats f on f.id = c.flat_id
            where f.id = ?1
             """, nativeQuery = true)
    Page<Claim> findUserClaimsByFlatId(UUID flatId, Pageable of);
}
