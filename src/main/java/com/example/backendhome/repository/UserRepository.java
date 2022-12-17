package com.example.backendhome.repository;

import com.example.backendhome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT u FROM User u
            JOIN FETCH u.contract c
            JOIN FETCH c.flat f
            where u.id = :id
            """)
    Optional<User> findById(UUID id);

    @Query(value = """
            SELECT u FROM User u
            JOIN FETCH u.contract c
            JOIN FETCH c.flat f
            where u.username = :username
            """)
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
