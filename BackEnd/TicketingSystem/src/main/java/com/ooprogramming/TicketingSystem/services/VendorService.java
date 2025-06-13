package com.ooprogramming.TicketingSystem.services;

import com.ooprogramming.TicketingSystem.Ticket;
import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.entities.EventVendorId;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import com.ooprogramming.TicketingSystem.repositories.EventVendorRepository;
import com.ooprogramming.TicketingSystem.repositories.VendorRepository;
import com.ooprogramming.TicketingSystem.TicketPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorEntity findVendorByUsername(String username) {
        return vendorRepository.findByVendorUserName(username);
    }

    public VendorEntity saveVendor(VendorEntity vendor) {
        return vendorRepository.save(vendor);
    }

    public Optional<VendorEntity> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }


    @Autowired
    private EventVendorRepository eventVendorRepository;


    @Autowired
    private EventRepository eventRepository;



    public List<EventVendorEntity> getTicketsforEvent(Long eventID) {
        return eventVendorRepository.findByEventEventid(eventID);
    }

    // Vendor Registration Method
    public VendorEntity createVendor(VendorEntity newVendor) {

        if (vendorRepository.existsByVendorUserName(newVendor.getVendorUserName())) {
            throw new RuntimeException("Vendor username is already in use");
        }

        return vendorRepository.save(newVendor);
    }

    // Vendor Login Method
    public boolean loginVendor(String vendorUserName, String vendorPassword) {
        VendorEntity vendor = vendorRepository.findByVendorUserName(vendorUserName);
        if (vendor != null) {
            // Compare the provided password with the stored password (no hashing)
            return vendorPassword.equals(vendor.getVendorPassword());
        }
        return false;
    }

    public Optional<EventVendorEntity> getEventVendor(Long eventID, Long vendorID) {
        EventVendorId eventVendorId = new EventVendorId();
        eventVendorId.setEvent(eventID);
        eventVendorId.setVendor(vendorID);
        return eventVendorRepository.findById(eventVendorId);
    }








    // Method to get all vendors
    public List<VendorEntity> getAllVendors() {
        return vendorRepository.findAll();
    }


//Method to assign vendor to an event with ticket amount
    public boolean addVendorToEvent(Long vendorId, Long eventId, Integer totalTickets) {


        Optional<VendorEntity> vendor = vendorRepository.findById(vendorId);
        Optional<EventEntity> event = eventRepository.findById(eventId);

        if (vendor.isPresent() && event.isPresent()) {
            EventEntity eventEntity = event.get();

            // Retrieve the current maxTicketCapacity for the event
            int maxTicketCapacity = eventEntity.getMaxTicketCapacity();

            // Calculate the current total of tickets released by all vendors
            int currentTotalTickets = eventVendorRepository.findByEventEventid(eventId)
                    .stream()
                    .mapToInt(EventVendorEntity::getTotalTickets)
                    .sum();

            // Check tickets would exceed the maxTicketCapacity
            if (currentTotalTickets + totalTickets > maxTicketCapacity) {
                throw new RuntimeException("Total tickets for this event would exceed the max capacity of " + maxTicketCapacity);
            }


            EventVendorEntity eventVendor = new EventVendorEntity();


            EventVendorId eventVendorId = new EventVendorId();
            eventVendorId.setEvent(eventId);
            eventVendorId.setVendor(vendorId);
            eventVendor.setId(eventVendorId);


            eventVendor.setVendor(vendor.get());
            eventVendor.setEvent(eventEntity);

            // Set the totalTickets
            eventVendor.setTotalTickets(totalTickets);

            // Save to db
            eventVendorRepository.save(eventVendor);

            return true;
        }

        return false;
    }






}
