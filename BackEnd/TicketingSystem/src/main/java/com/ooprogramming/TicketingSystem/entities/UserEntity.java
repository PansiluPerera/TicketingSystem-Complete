package com.ooprogramming.TicketingSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

//    autogenertate user id or customer id
    private Long userID;
    private String username;
    private String password;

    public UserEntity() {}

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //    setters


    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    getters


    public Long getUserID() {
        return userID;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
