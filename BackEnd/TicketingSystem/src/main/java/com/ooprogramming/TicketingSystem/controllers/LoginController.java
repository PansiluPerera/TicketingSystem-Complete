package com.ooprogramming.TicketingSystem.controllers;

import com.ooprogramming.TicketingSystem.entities.UserEntity;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;
import com.ooprogramming.TicketingSystem.services.AdminService;
import com.ooprogramming.TicketingSystem.services.UserService;
import com.ooprogramming.TicketingSystem.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final UserService userService;
    private final VendorService vendorService;
    private final AdminService adminService;

    public LoginController(UserService userService, VendorService vendorService, AdminService adminService) {
        this.userService = userService;
        this.vendorService = vendorService;
        this.adminService = adminService;
    }

    // Customer Registration Method
    @PostMapping("/register/customer")
    public ResponseEntity<String> registerCustomer(@RequestParam String username, @RequestParam String password) {
        // validate all fields
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password must be provided");
        }

        UserEntity newCustomer = new UserEntity(username, password);
        UserEntity registeredCustomer = userService.registerUser(newCustomer);


        return ResponseEntity.ok("Customer registered successfully: " + registeredCustomer.getUserName());
    }


    // Customer Login Method
    @PostMapping("/login/customer")
    public ResponseEntity<String> loginCustomer(@RequestParam String username, @RequestParam String password) {
//        validate the login details
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password must be provided");
        }

        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Customer login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }


    // Vendor Registration method
    @PostMapping("/register/vendor")
    public ResponseEntity<String> registerVendor(@RequestParam String vendorName, @RequestParam String vendorUserName, @RequestParam String vendorPassword) {
        // Validate the inputs
        if (vendorName == null || vendorUserName == null || vendorPassword == null ||
                vendorName.isEmpty() || vendorUserName.isEmpty() || vendorPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("All fields must be provided");
        }

        VendorEntity newVendor = new VendorEntity(vendorName, vendorUserName, vendorPassword);
        VendorEntity registeredVendor = vendorService.createVendor(newVendor);
        return ResponseEntity.ok("Vendor registration successful: " + registeredVendor.getVendorName());
    }

    // Vendor Login method
    @PostMapping("/login/vendor")
    public ResponseEntity<String> loginVendor(
            @RequestParam String vendorUserName,
            @RequestParam String vendorPassword
    ) {
        // Validate the inputs for loggin vendor
        if (vendorUserName == null || vendorPassword == null || vendorUserName.isEmpty() || vendorPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password must be provided");
        }

        boolean isAuthenticated = vendorService.loginVendor(vendorUserName, vendorPassword);
        if (isAuthenticated) {
            return ResponseEntity.ok("Vendor login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid vendor username or password");
        }
    }



    // Admin Login method
    @PostMapping("/login/admin")
    public ResponseEntity<String> loginAdmin(@RequestParam String adminUserName, @RequestParam String adminPassword) {
        boolean isAuthenticated = adminService.authenticateAdmin(adminUserName, adminPassword);
        if (isAuthenticated) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid admin username or password");
        }
    }
}



