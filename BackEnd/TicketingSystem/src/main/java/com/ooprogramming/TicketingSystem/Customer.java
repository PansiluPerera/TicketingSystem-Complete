package com.ooprogramming.TicketingSystem;

import com.ooprogramming.TicketingSystem.entities.UserEntity;
import com.ooprogramming.TicketingSystem.entities.TicketPurchaseEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import com.ooprogramming.TicketingSystem.repositories.TicketPurchaseRepository;
import com.ooprogramming.TicketingSystem.entities.EventEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final UserEntity user;
    private final long eventId;
    private final TicketPurchaseRepository ticketPurchaseRepository;
    private final EventRepository eventRepository;// Repository for DB interaction


    // Constructor accepts the repository for DB interaction
    public Customer(TicketPool ticketPool, UserEntity user, long eventId, TicketPurchaseRepository ticketPurchaseRepository, EventRepository eventRepository) {
        this.ticketPool = ticketPool;
        this.user = user;
        this.eventId = eventId;
        this.ticketPurchaseRepository = ticketPurchaseRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run() {
        // Retrieve the EventEntity from the repository using eventId
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found for eventId: " + eventId));


        Integer customerRetrievalRate = event.getCustomerRetrievalRate();

        int totalTicketsForEvent = ticketPool.getMaxTicketsForEvent(eventId);
        Set<String> assignedCustomers = new HashSet<>();


        AtomicBoolean stopSignal = ticketPool.getStopSignal(eventId);

        while (assignedCustomers.size() < totalTicketsForEvent) {

            if (stopSignal.get()) {
                System.out.println("Customer thread stopping for event " + eventId);
                break;
            }


            Ticket ticket = ticketPool.retrieveTicket(eventId, user.getUserName());

            if (ticket != null) {
                // assign it to the user
                assignTicketToUser(user, ticket);
                assignedCustomers.add(user.getUserName()); // Mark this customer as assigned
            }

            // Simulate a delay using the customerRetrievalRate
            try {
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit loop on interruption
            }
        }

        System.out.println(user.getUserName() + " finished retrieving tickets for event " + eventId);
    }


    // Method to assign ticket to the user
    private void assignTicketToUser(UserEntity user, Ticket ticket) {
        System.out.println("Assigned ticket " + ticket.getTicketId() + " to user " + user.getUserName());

        // Now insert the ticket purchase log into the database
        TicketPurchaseEntity ticketPurchase = new TicketPurchaseEntity();
        ticketPurchase.setTicketID(ticket.getTicketId());
        ticketPurchase.setEvent(ticket.getEvent());
        ticketPurchase.setVendor(ticket.getVendor());
        ticketPurchase.setUser(user);
        ticketPurchase.setPurchaseDate(LocalDate.now());
        ticketPurchase.setPurchased(true);

        // Save the ticket purchase to db
        ticketPurchaseRepository.save(ticketPurchase);
    }
}
