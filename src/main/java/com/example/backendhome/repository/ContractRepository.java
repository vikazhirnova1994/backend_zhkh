package com.example.backendhome.repository;

import com.example.backendhome.entity.Contract;
import com.example.backendhome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository  extends JpaRepository<Contract, UUID> {

    @Query(value = """
            SELECT c FROM Contract c
            JOIN FETCH c.flat f
            where c.contractNumber = :contractNumber
            """)
    Optional<Contract>  findContractWithFlat(String contractNumber);

    Optional<Contract> findContractByContractNumber(String contractNumber);

    Boolean existsContractByContractNumber(String contractNumber);

}
