package com.ooprogramming.TicketingSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AdminEntity {

//    admin ID is set automatically
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long adminID;
    private String adminUserName;
    private String adminPassword;

//    setters for variables


    public void setAdminID(Long adminID) {
        this.adminID = adminID;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

//    gettters for variables


    public Long getAdminID() {
        return adminID;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}



//THERE IS NO METHOD TO SET THE ADMIN AS THE ADMIN IS CREATED WHEN THE DATABASE IS CREATED AS A RAW VALUE