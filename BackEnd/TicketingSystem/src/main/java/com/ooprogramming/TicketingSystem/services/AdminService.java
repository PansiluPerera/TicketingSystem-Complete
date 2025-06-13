package com.ooprogramming.TicketingSystem.services;

import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooprogramming.TicketingSystem.entities.AdminEntity;
import com.ooprogramming.TicketingSystem.repositories.AdminRepository;


import java.util.List;
import java.util.Optional;

@Service

public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired  // Ensure this annotation is present
    private EventRepository eventRepository;

    public EventEntity createEvent(EventEntity event) {
        return eventRepository.save(event);
    }

//    public static List<EventEntity> getAllEvents() {
//
//        return List.of();
//    }
//
//    public static void startTicketSales(Long eventId) {
//
//    }
//
//
//    public AdminEntity findAdminByUsername(String username) {
//        return adminRepository.findByadminUserName(username);
//    }
//
//    public AdminEntity saveAdmin(AdminEntity admin) {
//        return adminRepository.save(admin);
//    }
//
//    public Optional<AdminEntity> getAdminById(Long id) {
//        return adminRepository.findById(id);
//    }

    // Admin Login
    public boolean authenticateAdmin(String adminUserName, String adminPassword) {
        // Assume there is only one admin, so we fetch the first record
        AdminEntity admin = adminRepository.findFirstByAdminUserName(adminUserName);

        if (admin != null) {
            // Compare the provided password with the stored password (plaintext comparison)
            return adminPassword.equals(admin.getAdminPassword());
        }
        return false;
    }

}


