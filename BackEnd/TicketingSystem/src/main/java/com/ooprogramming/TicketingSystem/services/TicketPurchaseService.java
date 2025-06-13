package com.ooprogramming.TicketingSystem.services;

import com.ooprogramming.TicketingSystem.Ticket;
import com.ooprogramming.TicketingSystem.TicketPool;
import com.ooprogramming.TicketingSystem.entities.*;
import com.ooprogramming.TicketingSystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketPurchaseService {

    //private final Queue<UserEntity> ticketQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private TicketPurchaseRepository ticketPurchaseRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;


    // Assign ticket to user
//    private void assignTicketToUser(UserEntity user, Ticket ticket) {
//        TicketPurchaseEntity ticketPurchase = new TicketPurchaseEntity();
//        ticketPurchase.setUser(user);
//        ticketPurchase.setEvent(ticket.getEvent());
//        ticketPurchase.setVendor(ticket.getVendor());
//        ticketPurchase.setPurchaseDate(LocalDate.now());
//        ticketPurchase.setPurchased(true);
//
//        ticketPurchaseRepository.save(ticketPurchase);
//    }

    // Method to add user to the queue
//    public void addUserToQueue(Long userId, Long eventId) {
//        UserEntity user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        EventEntity event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        ticketQueue.add(user);
//        System.out.println("User added to queue: " + user.getUserName());
//    }

    public List<TicketPurchaseEntity> getTicketsByUserId(Long userId) {
        return ticketPurchaseRepository.findByUser_UserID(userId);
    }

    // Method to get all ticket purchases for a specific event
    public List<TicketPurchaseEntity> getTicketPurchasesByEventId(Long eventId) {
        return ticketPurchaseRepository.findByEventEventid(eventId);
    }

    // Method to count the number of tickets purchased per vendor for a specific event
    public Map<Long, Long> getTicketCountPerVendorForEvent(Long eventId) {
        List<TicketPurchaseEntity> ticketPurchases = ticketPurchaseRepository.findByEventEventid(eventId);

        // Create a map to store the vendorId
        Map<Long, Long> vendorTicketCount = new HashMap<>();


        for (TicketPurchaseEntity ticketPurchase : ticketPurchases) {
            Long vendorId = ticketPurchase.getVendor().getVendorID();
            vendorTicketCount.put(vendorId, vendorTicketCount.getOrDefault(vendorId, 0L) + 1);
        }

        return vendorTicketCount;
    }

}


