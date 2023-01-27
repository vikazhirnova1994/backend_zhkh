package com.example.backendhome.repository;

import com.example.backendhome.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

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
            """)
    List<User> findAllUsers();

    @Query(value = """
            SELECT u FROM User u
            JOIN FETCH u.contract c
            JOIN FETCH c.flat f
            where u.username = :username
            """)
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value =
            """
            select * from db_users
            left join contract c on c.id = db_users.contract_id
            left join user_roles ur on ur.user_id = db_users.id
            left join db_roles dr on dr.id = ur.role_id
            where db_users.id != ?1
            """,
            countQuery = """
            select count(*)  from db_users
            left join contract c on c.id = db_users.contract_id
            left join user_roles ur on ur.user_id = db_users.id
            left join db_roles dr on dr.id = ur.role_id
            where db_users.id != ?1
            """,
            nativeQuery = true)
    Page<User> findUsers(UUID id, Pageable of);

    @Query(value = """
            SELECT u FROM User u
            JOIN FETCH u.contract c
            JOIN FETCH c.flat f
            """)
    Optional<User> findUserFlat(UUID id);
}
