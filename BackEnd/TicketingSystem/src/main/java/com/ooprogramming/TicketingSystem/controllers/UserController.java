package com.ooprogramming.TicketingSystem.controllers;


import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.UserEntity;
import com.ooprogramming.TicketingSystem.entities.UserEventEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import com.ooprogramming.TicketingSystem.repositories.UserEventRepository;
import com.ooprogramming.TicketingSystem.repositories.UserRepository;
import com.ooprogramming.TicketingSystem.services.TicketPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ooprogramming.TicketingSystem.entities.TicketPurchaseEntity;
import com.ooprogramming.TicketingSystem.services.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/user")


public class UserController {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final UserEventRepository userEventRepository;

    @Autowired
    public UserController(UserRepository userRepository, EventRepository eventRepository, UserEventRepository userEventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
    }

    @Autowired
    private UserService userService;


//get the tickets for a specific user
    @GetMapping("/{userId}/tickets")
    public ResponseEntity<List<TicketPurchaseEntity>> getUserTickets(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserTickets(userId));
    }


// set a user into a certain event based on user id so that user is on a queue to buy tickets
    @PostMapping("/select-event/{userId}/{eventId}")
    public ResponseEntity<String> selectEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        // Check if the event exists, and the user is valid
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        // Create and save the UserEventEntity to map the user to the event
        UserEventEntity userEvent = new UserEventEntity();
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEventRepository.save(userEvent);

        return ResponseEntity.ok("User added to the event.");
    }

//    find the cusotmers based on a username input
    @GetMapping("/find")
    public ResponseEntity<List<UserEntity>> findCustomer(@RequestParam String username) {
        List<UserEntity> customers = userService.findUsersByUsername(username);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(customers);
    }

//    find all customers in the database
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllCustomers() {
        List<UserEntity> customers = userService.getAllUsers();  // Call service to get all users
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if no users found
        }
        return ResponseEntity.ok(customers);  // Return 200 OK with the list of users
    }

}
