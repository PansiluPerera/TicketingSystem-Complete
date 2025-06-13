package com.ooprogramming.TicketingSystem.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class TicketPurchaseEntity {

//    ticketid is generated according to the eventid, vendor id and timestamp

    @Id

    private String ticketID;

    @ManyToOne
    @JoinColumn(name = "event_id")

    private EventEntity event;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private VendorEntity vendor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDate purchaseDate;
    private boolean isPurchased;

    //    getters


    public String getTicketID() {
        return ticketID;
    }

    public EventEntity getEvent() {
        return event;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public UserEntity getUser() {
        return user;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

//    settesrs


    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }



    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
