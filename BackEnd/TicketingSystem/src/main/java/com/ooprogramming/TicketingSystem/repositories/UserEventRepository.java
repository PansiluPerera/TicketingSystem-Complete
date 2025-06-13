package com.ooprogramming.TicketingSystem.repositories;

import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.UserEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEventEntity, Long> {

    public List<UserEventEntity> findByEvent(EventEntity event); // Fetch all UserEventEntities by eventId
}
