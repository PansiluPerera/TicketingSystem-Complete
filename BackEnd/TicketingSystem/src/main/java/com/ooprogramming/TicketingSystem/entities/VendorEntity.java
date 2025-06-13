package com.ooprogramming.TicketingSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class VendorEntity {

//    generate the vendor id automatically

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long vendorID;
    private String vendorName;
    private String vendorUserName;
    private String vendorPassword;

    public VendorEntity() {}

    public VendorEntity(String vendorName, String vendorUserName, String vendorPassword) {
        this.vendorName = vendorName;
        this.vendorUserName = vendorUserName;
        this.vendorPassword = vendorPassword;
    }

    //    getters for variables


    public Long getVendorID() {
        return vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorUserName() {
        return vendorUserName;
    }
    public String getVendorPassword() {
        return vendorPassword;
    }

//    setters for variables


    public void setVendorID(Long vendorID) {
        this.vendorID = vendorID;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setVendorUserName(String vendorUserName) {
        this.vendorUserName = vendorUserName;
    }

    public void setVendorPassword(String vendorPassword) {
        this.vendorPassword = vendorPassword;
    }
}

