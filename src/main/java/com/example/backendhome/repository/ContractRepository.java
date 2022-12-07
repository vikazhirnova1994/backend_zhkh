package com.example.backendhome.repository;

import com.example.backendhome.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository  extends JpaRepository<Contract, UUID> {
    Optional<Contract> findContractByContractNumber(String contractNumber);

    Boolean existsContractByContractNumber(String contractNumber);

/*
    @Query(value = """
            SELECT c FROM Contract c
            JOIN FETCH c.user
            JOIN FETCH ac.agreements ag
            JOIN FETCH ag.productId p
            WHERE ac.clientId=:clientId AND ac.isActive=true AND
            c.isDefault = true AND ag.isActive =true AND c.id = :cardId AND ag.id = :agreementId
            """)
    Optional<Contract> findContractByUserName(String userName);
*/
   // Optional<Contract> findContractByUser(User user);
}
