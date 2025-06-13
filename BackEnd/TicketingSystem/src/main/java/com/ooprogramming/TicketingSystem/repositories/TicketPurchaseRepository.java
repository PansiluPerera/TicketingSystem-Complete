package com.ooprogramming.TicketingSystem.repositories;

import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.TicketPurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TicketPurchaseRepository extends JpaRepository<TicketPurchaseEntity, Long> {

    List<TicketPurchaseEntity> findByUser_UserID(Long userId);
    List<TicketPurchaseEntity> findByEventAndIsPurchasedFalse(EventEntity event);

    List<TicketPurchaseEntity> findByEventEventid(Long eventId);


}






