package com.ooprogramming.TicketingSystem.entities;

import jakarta.persistence.*;

@Entity
public class UserEventEntity {

//    generated usereventid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    foreign keys to connect user id with an event id to add to queue
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }
}
