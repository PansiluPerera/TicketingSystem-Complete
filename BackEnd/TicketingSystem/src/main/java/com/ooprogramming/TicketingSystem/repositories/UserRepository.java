package com.ooprogramming.TicketingSystem.repositories;

import com.ooprogramming.TicketingSystem.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findAll();

    List<UserEntity> findByUsernameContaining(String username);




}
