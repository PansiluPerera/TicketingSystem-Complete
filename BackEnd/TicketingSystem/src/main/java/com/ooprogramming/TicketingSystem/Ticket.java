package com.ooprogramming.TicketingSystem;

import com.ooprogramming.TicketingSystem.entities.EventEntity;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;

import java.util.UUID;

public class Ticket {
        private String ticketId;
        private EventEntity event;
        private VendorEntity vendor;

        public Ticket(String ticketId, EventEntity event, VendorEntity vendor) {
            this.ticketId = ticketId;
            this.event = event;
            this.vendor = vendor;
        }


    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public String getTicketId() {
            return ticketId;
        }

        public EventEntity getEvent() {
            return event;
        }

        public VendorEntity getVendor() {
            return vendor;
        }

}
