package com.example.shipon.toletbd.models;

/**
 * Created by Shipon on 9/24/2018.
 */

public class Owner {
    private String ownerName;
    private String ownerContact;
    private String ownerOccupation;
    private String ownerAddress;
    private String ownerPassword;
    public Owner(String ownerName, String ownerContact, String ownerOccupation, String ownerAddress, String ownerPassword) {
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.ownerOccupation = ownerOccupation;
        this.ownerAddress = ownerAddress;
        this.ownerPassword = ownerPassword;
    }
    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public String getOwnerOccupation() {
        return ownerOccupation;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }





}
