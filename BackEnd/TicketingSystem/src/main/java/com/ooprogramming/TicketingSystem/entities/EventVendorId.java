package com.ooprogramming.TicketingSystem.entities;

import java.io.Serializable;
import java.util.Objects;


//this class is an extra class created to set the composite key for EventvendorEntity
public class EventVendorId implements Serializable {
    private Long event;
    private Long vendor;

    // Default constructor
    public EventVendorId() {}

    // Getters and setters
    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public Long getVendor() {
        return vendor;
    }

    public void setVendor(Long vendor) {
        this.vendor = vendor;
    }

//    override method to set the event and vendor objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventVendorId that = (EventVendorId) o;
        return Objects.equals(event, that.event) &&
                Objects.equals(vendor, that.vendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, vendor);
    }
}
