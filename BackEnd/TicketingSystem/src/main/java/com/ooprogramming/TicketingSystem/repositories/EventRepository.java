package com.ooprogramming.TicketingSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ooprogramming.TicketingSystem.entities.EventEntity;
import org.springframework.stereotype.Repository;

@Repository

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
