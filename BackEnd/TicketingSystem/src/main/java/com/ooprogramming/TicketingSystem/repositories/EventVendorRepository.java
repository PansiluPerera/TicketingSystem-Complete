package com.ooprogramming.TicketingSystem.repositories;

import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.entities.EventVendorId;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventVendorRepository extends JpaRepository<EventVendorEntity, EventVendorId> {
    List<EventVendorEntity> findByEventEventid(Long eventId);

    Optional<EventVendorEntity> findByEventAndVendor(EventEntity event, VendorEntity vendor);

    List<EventVendorEntity> findByEvent(EventEntity event);


    @Query("SELECT ev FROM EventVendorEntity ev WHERE ev.event.id = :eventId")
    List<EventVendorEntity> findByEventId(@Param("eventId") long eventId);



}

