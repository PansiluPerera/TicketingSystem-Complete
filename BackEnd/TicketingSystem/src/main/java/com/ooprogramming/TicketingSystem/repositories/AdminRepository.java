package com.ooprogramming.TicketingSystem.repositories;

import com.ooprogramming.TicketingSystem.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    AdminEntity findByadminUserName(String username);
    AdminEntity findFirstByAdminUserName(String adminUserName);

}

