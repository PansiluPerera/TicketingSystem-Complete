package com.ooprogramming.TicketingSystem;

import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import com.ooprogramming.TicketingSystem.repositories.VendorRepository;

import java.util.concurrent.atomic.AtomicBoolean;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final EventVendorEntity eventVendorEntity;
    private final VendorRepository vendorRepository;
    private final EventRepository eventRepository;



    public Vendor(TicketPool ticketPool, EventVendorEntity eventVendorEntity, VendorRepository vendorRepository, EventRepository eventRepository) {
        this.ticketPool = ticketPool;
        this.eventVendorEntity = eventVendorEntity;
        this.vendorRepository = vendorRepository;
        this.eventRepository = eventRepository;
    }




    @Override
    public void run() {
        long eventId = eventVendorEntity.getId().getEvent();
        int totalTickets = eventVendorEntity.getTotalTickets();
        Long vendorId = eventVendorEntity.getId().getVendor();


        VendorEntity vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found for vendorId: " + vendorId));


        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found for eventId: " + eventId));

        // Extract ticketReleaseRate
        Integer ticketReleaseRate = event.getTicketReleaseRate();

        // Add initialization for the ticket pool
        ticketPool.initializeEvent(eventId, totalTickets);

        // stop signal for the event
        AtomicBoolean stopSignal = ticketPool.getStopSignal(eventId);

        for (int i = 0; i < totalTickets; i++) {
            // Check the stop signal
            if (stopSignal.get()) {
                System.out.println("Vendor thread stopping for event " + eventId);
                break;
            }

            // Create and add a ticket to the pool
            Ticket ticket = new Ticket(generateTicketId(eventId, vendorId), eventVendorEntity.getEvent(), vendor);
            ticketPool.addTicket(eventId, ticket);

            try {
                // Use ticketReleaseRate for thread sleep
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("Vendor " + vendor.getVendorName() + " finished adding tickets for event " + eventId);
    }



    private String generateTicketId(long eventId, long vendorId) {
        return String.format("%d%d%d", eventId, vendorId, System.nanoTime());
    }
}
