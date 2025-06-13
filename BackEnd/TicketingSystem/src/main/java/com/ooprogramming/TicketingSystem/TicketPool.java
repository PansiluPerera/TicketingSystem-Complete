package com.ooprogramming.TicketingSystem;

import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TicketPool {
    private final Map<Long, Queue<Ticket>> eventTicketPools = new HashMap<>();
    private final Map<Long, Integer> maxTicketsPerEvent = new HashMap<>();

    private final ConcurrentHashMap<Long, AtomicBoolean> stopSignals = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, List<Thread>> eventThreads = new ConcurrentHashMap<>();

    private final EventRepository eventRepository;

    public TicketPool(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Inside TicketPool class
    private Set<String> assignedCustomers = new HashSet<>();

    // Initialize ticket pool for an event
    public synchronized void initializeEvent(long eventId, int totalTickets) {
        if (!eventTicketPools.containsKey(eventId)) {
            eventTicketPools.put(eventId, new LinkedList<>());
            maxTicketsPerEvent.put(eventId, totalTickets);

            // stop signal
            stopSignals.put(eventId, new AtomicBoolean(false));
        }
    }



    // Method to get or create a stop signal for an event
    public AtomicBoolean getStopSignal(long eventId) {
        return stopSignals.computeIfAbsent(eventId, id -> new AtomicBoolean(false));
    }
    // Method to register a thread for an event
    public void registerThread(long eventId, Thread thread) {
        eventThreads.computeIfAbsent(eventId, id -> new ArrayList<>()).add(thread);
    }

    // Method to get all threads for an event
    public List<Thread> getEventThreads(long eventId) {
        return eventThreads.getOrDefault(eventId, new ArrayList<>());
    }

    // method to clear threads
    public void clearEventThreads(long eventId) {
        eventThreads.remove(eventId);
    }





    // Add tickets to a specific event's ticket pool
    public synchronized void addTicket(long eventId, Ticket ticket) {
        Queue<Ticket> tickets = eventTicketPools.get(eventId);
        int maxTickets = maxTicketsPerEvent.get(eventId);

        while (tickets.size() >= maxTickets) {
            try {
                wait(); // Wait until there's space in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        tickets.add(ticket);
        System.out.println(Thread.currentThread().getName() + " added ticket for event " + eventId + ": " + ticket.getTicketId());
        notifyAll(); // Notify other threads
    }


    // Retrieve a ticket from the ticket pool
    public synchronized Ticket retrieveTicket(long eventId, String userName) {
        if (assignedCustomers.contains(userName)) {
            return null; // Skip ticket retrieval for already assigned customers
        }

        Queue<Ticket> tickets = eventTicketPools.get(eventId);
        while (tickets == null || tickets.isEmpty()) {
            try {
                System.out.println(userName + " is waiting for tickets for event " + eventId + "...");
                wait(); // Wait for a ticket to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Ticket ticket = tickets.poll();
        if (ticket != null) {
            assignedCustomers.add(userName);
            System.out.println(userName + " retrieved ticket for event " + eventId + ": " + ticket.getTicketId());
        }
        notifyAll(); // Notify vendors
        return ticket;
    }

    public int getMaxTicketsForEvent(long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found for eventId: " + eventId));
        return eventEntity.getMaxTicketCapacity();
    }





}