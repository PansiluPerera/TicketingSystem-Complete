package com.ooprogramming.TicketingSystem.controllers;

import com.ooprogramming.TicketingSystem.AddVendorToEventRequest;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;
import com.ooprogramming.TicketingSystem.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Endpoint to create a new vendor
    @PostMapping("/create")
    public ResponseEntity<VendorEntity> createVendor(@RequestBody VendorEntity vendorEntity) {
        VendorEntity createdVendor = vendorService.createVendor(vendorEntity);
        return ResponseEntity.ok(createdVendor);
    }

    // Method to get all vendors
    @GetMapping("/getVendors")
    public ResponseEntity<List<VendorEntity>> getAllVendors() {
        List<VendorEntity> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

//    method to get a vendor by vendor id
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorEntity> getVendorById(@PathVariable Long vendorId) {
        Optional<VendorEntity> vendor = vendorService.getVendorById(vendorId);

        return vendor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Method to let a vendor set ticket amount for an event
    @PostMapping("/{vendorId}/events/{eventId}")
    public ResponseEntity<String> addVendorToEvent(@PathVariable Long vendorId,
                                                   @PathVariable Long eventId,
                                                   @RequestBody AddVendorToEventRequest request) {
        boolean isAdded = vendorService.addVendorToEvent(vendorId, eventId, request.getTotalTickets());
        if (isAdded) {
            return ResponseEntity.ok("Vendor added to event successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to add vendor to event.");
        }
    }
}