package com.ooprogramming.TicketingSystem.services;

import com.ooprogramming.TicketingSystem.Customer;
import com.ooprogramming.TicketingSystem.Ticket;
import com.ooprogramming.TicketingSystem.TicketPool;
import com.ooprogramming.TicketingSystem.Vendor;
import com.ooprogramming.TicketingSystem.entities.*;
import com.ooprogramming.TicketingSystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@Service



public class EventService {


    @Autowired
    private EventRepository eventRepository;


    public List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    public EventEntity saveEvent(EventEntity event) {
        return eventRepository.save(event);
    }

    public Optional<EventEntity> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Admin creates a new event
    public void createEvent(EventEntity event) {
        eventRepository.save(event);
    }


    private final TicketPool ticketPool;
    private final TicketPurchaseRepository ticketPurchaseRepository;
    private final UserEventRepository userEventRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public EventService(TicketPool ticketPool, TicketPurchaseRepository ticketPurchaseRepository, UserEventRepository userEventRepository, VendorRepository vendorRepository) {
        this.ticketPool = ticketPool;
        this.ticketPurchaseRepository = ticketPurchaseRepository;
        this.userEventRepository = userEventRepository;
        this.vendorRepository = vendorRepository;
    }




    /**
     * Starts the ticket sales for an event by initializing the ticket pool and creating threads for vendors and customers.
     * @param eventId The ID of the event for which sales are starting
     * @param vendors List of EventVendorEntity that will be selling tickets for the event
     */
    public void startSales(long eventId, List<EventVendorEntity> vendors) {

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Calculate the total tickets for the event
        int totalTickets = vendors.stream()
                .mapToInt(EventVendorEntity::getTotalTickets)
                .sum();

        // Initialize the event's ticket pool
        ticketPool.initializeEvent(eventId, totalTickets);

        // Start vendor threads and register them
        for (EventVendorEntity vendorEntity : vendors) {

            Long vendorId = vendorEntity.getId().getVendor();


            VendorEntity vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found for vendorId: " + vendorId));


            Vendor vendorRunnable = new Vendor(ticketPool, vendorEntity, vendorRepository, eventRepository);


            Thread vendorThread = new Thread(vendorRunnable, "Vendor " + vendor.getVendorName());

            // Register the vendor thread
            ticketPool.registerThread(eventId, vendorThread); // Register thread


            vendorThread.start();
        }


        List<UserEventEntity> userEvents = userEventRepository.findByEvent(event);

        // Start customer threads and register them in the TicketPool
        for (UserEventEntity userEvent : userEvents) {
            UserEntity customer = userEvent.getUser();


            Customer customerRunnable = new Customer(ticketPool, customer, eventId, ticketPurchaseRepository, eventRepository);


            Thread customerThread = new Thread(customerRunnable, "Customer " + customer.getUserName());

            // Register the customer thread
            ticketPool.registerThread(eventId, customerThread);


            customerThread.start();
        }
    }

//method to stop the sales
    public void stopSales(long eventId) {
        // send the stop signal
        AtomicBoolean stopSignal = ticketPool.getStopSignal(eventId);
        if (stopSignal != null) {
            stopSignal.set(true);


            List<Thread> threads = ticketPool.getEventThreads(eventId);
            if (threads != null) {
                for (Thread thread : threads) {
                    thread.interrupt(); // Interrupt the thread to wake it if sleeping
                }
            }

//            clear threads
            ticketPool.clearEventThreads(eventId);

            System.out.println("Stopped sales for event " + eventId);
        } else {
            throw new RuntimeException("Event not found or sales not started for eventId: " + eventId);
        }
    }






}





