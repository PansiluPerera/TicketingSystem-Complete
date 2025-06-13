package com.ooprogramming.TicketingSystem.entities;

import jakarta.persistence.*;

@Entity
public class EventVendorEntity {


//    Embedded id as this is a table connecting Event and vendor tables
    @EmbeddedId
    private EventVendorId id;


//    foreign keys from vendor and event tables
    @ManyToOne
    @MapsId("vendor")  // Map to vendor field in the composite key
    @JoinColumn(name = "vendor_id", insertable = false, updatable = false)
    private VendorEntity vendor;

    @ManyToOne
    @MapsId("event")  // Map to event field in the composite key
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private EventEntity event;

    private Integer totalTickets;

    // Default constructor
    public EventVendorEntity() {}


    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    // Getter and Setter methods for the event
    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }


    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public EventVendorId getId() {
        return id;
    }

    public void setId(EventVendorId id) {
        this.id = id;
    }
}

