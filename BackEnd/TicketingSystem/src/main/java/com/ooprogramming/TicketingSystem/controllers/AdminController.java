package com.ooprogramming.TicketingSystem.controllers;

import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.entities.UserEntity;
import com.ooprogramming.TicketingSystem.repositories.EventVendorRepository;
import com.ooprogramming.TicketingSystem.repositories.UserRepository;
import com.ooprogramming.TicketingSystem.services.EventService;
import com.ooprogramming.TicketingSystem.services.TicketPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import com.ooprogramming.TicketingSystem.services.AdminService;


import java.util.List;

@RestController

@RequestMapping("/api/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

//importing the other classes as objects
    @Autowired
    private EventRepository eventRepository;


    private final EventService eventService;
    private final EventVendorRepository eventVendorRepository;  // To fetch vendors
    private final UserRepository userRepository;

    @Autowired
    public AdminController(EventService eventService, EventVendorRepository eventVendorRepository, UserRepository userRepository) {
        this.eventService = eventService;
        this.eventVendorRepository = eventVendorRepository;
        this.userRepository = userRepository;
    }


//controller method for creating an event
    @PostMapping("/create-event")
    public ResponseEntity<String> createEvent(@RequestBody EventEntity event) {
        eventService.createEvent(event);
        return ResponseEntity.ok("Event created successfully!");
    }

//    controller method for starting sales with event parameter
    @PostMapping("/startSales")
    public String startTicketSales(@RequestParam("eventId") long eventid) {
        try {

            List<EventVendorEntity> vendors = eventVendorRepository.findByEventId(eventid);

            // Call the startSales method
            eventService.startSales(eventid, vendors);

            return "Ticket sales started successfully for event with ID: " + eventid;
        } catch (Exception e) {
            return "Error starting ticket sales for event with ID: " + eventid + " - " + e.getMessage();
        }
    }


    // controller method for stopping sales of a specific event
    @PostMapping("/stopSales")
    public ResponseEntity<String> stopSales(@RequestParam long eventId) {
        try {
            // Call stopSales method
            eventService.stopSales(eventId);
            return ResponseEntity.ok("Sales stopped for event ID " + eventId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error stopping sales: " + e.getMessage());
        }
    }



//    method to call every event available from database
    @GetMapping("/events")
    public List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }


}
