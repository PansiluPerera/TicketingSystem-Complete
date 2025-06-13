package com.ooprogramming.TicketingSystem.services;
import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.repositories.EventVendorRepository;

import java.util.List;
import java.util.Optional;

@Service



public class EventVendorService {

    @Autowired
    private EventVendorRepository eventVendorRepository;
    @Autowired
    private EventRepository eventRepository;


    public List<EventVendorEntity> getAllEventVendors() {
        return eventVendorRepository.findAll();
    }



    public EventVendorEntity saveEventVendor(EventVendorEntity eventVendor) {
        return eventVendorRepository.save(eventVendor);
    }

//    @Transactional
//    public EventVendorEntity setTotalTickets(EventVendorEntity eventVendor) {
//        if (eventVendor.getEvent() == null || eventVendor.getVendor() == null || eventVendor.getTotalTickets() == null) {
//            throw new IllegalArgumentException("Event, Vendor, or Total Tickets cannot be null");
//        }
//
//        // Logic to save the EventVendorEntity
//        return eventVendorRepository.save(eventVendor);
//    }
//
//    public Optional<EventEntity> getEventById(Long eventID) {
//        return eventRepository.findById(eventID);
//    }
//
//    public List<EventVendorEntity> getVendorsByEvent(long eventId) {
//        return eventVendorRepository.findByEventId(eventId);
//    }

}
