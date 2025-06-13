package com.ooprogramming.TicketingSystem.services;

import com.ooprogramming.TicketingSystem.entities.UserEntity;
import com.ooprogramming.TicketingSystem.repositories.UserRepository;
import com.ooprogramming.TicketingSystem.entities.TicketPurchaseEntity;
import com.ooprogramming.TicketingSystem.services.TicketPurchaseService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketPurchaseService ticketPurchaseService;

    // User Registration
    public UserEntity registerUser(UserEntity newUser) {
        // Check if username already exists
        if (userRepository.existsByUsername(newUser.getUserName())) {
            throw new RuntimeException("Username is already in use");
        }
        // Save the new user
        return userRepository.save(newUser);
    }

    // User Login
    public boolean authenticateUser(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }




    // Get User's Tickets using userid
    public List<TicketPurchaseEntity> getUserTickets(Long userId) {
        return ticketPurchaseService.getTicketsByUserId(userId);
    }

//    find all users with similar username input
    public List<UserEntity> findUsersByUsername(String username) {
        // Find all users with the given username
        return userRepository.findByUsernameContaining(username);  // Assuming you use Spring Data JPA's query derivation
    }

//    method to get all customers in the system
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();  // Retrieves all user entities from the table
    }
}
