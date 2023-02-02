package com.example.backendhome.repository;

import com.example.backendhome.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository  extends JpaRepository<Contract, UUID> {

    @Query(value = """
            select c from Contract c
            join fetch c.flat f
            where c.contractNumber = :contractNumber
            """)
    Optional<Contract>  findContractWithFlat(String contractNumber);

    @Query(value = """
            select * from contract
            left join flats f on f.id = contract.flat_id
            where contract.termination_date is null
            """, countQuery = """
            select count(*)  from contract
            left join flats f on f.id = contract.flat_id
            where contract.termination_date is null
            """, nativeQuery = true)
    Page<Contract> findContract(Pageable pageable);

    Optional<Contract> findContractByContractNumber(String contractNumber);

    Boolean existsContractByContractNumber(String contractNumber);
}
