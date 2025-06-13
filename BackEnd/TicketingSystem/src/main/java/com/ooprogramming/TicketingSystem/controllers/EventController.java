package com.ooprogramming.TicketingSystem.controllers;

import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.entities.TicketPurchaseEntity;
import com.ooprogramming.TicketingSystem.entities.UserEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import com.ooprogramming.TicketingSystem.repositories.UserRepository;
import com.ooprogramming.TicketingSystem.services.EventService;
import com.ooprogramming.TicketingSystem.entities.EventEntity;


import com.ooprogramming.TicketingSystem.services.TicketPurchaseService;
import com.ooprogramming.TicketingSystem.services.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketPurchaseService ticketPurchaseService;



    @Autowired
    private VendorService vendorService;

//    method to list events

    @GetMapping("/all")
    public ResponseEntity<List<EventEntity>> getAllEvents() {
        List<EventEntity> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

//method to get event by a certain eventid
    @GetMapping("/{eventID}")
    public ResponseEntity<EventEntity> getEventById(@PathVariable Long eventID) {
        return eventService.getEventById(eventID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // method to get all ticket purchases for a specific event id
    @GetMapping("/{eventId}/ticket-purchases")
    public List<TicketPurchaseEntity> getTicketPurchasesForEvent(@PathVariable Long eventId) {
        return ticketPurchaseService.getTicketPurchasesByEventId(eventId);
    }

    // method to count the tickets sold per vendor in a specific event
    @GetMapping("/{eventId}/ticket-count-per-vendor")
    public Map<Long, Long> getTicketCountPerVendor(@PathVariable Long eventId) {
        return ticketPurchaseService.getTicketCountPerVendorForEvent(eventId);
    }



}



