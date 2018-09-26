package com.example.shipon.toletbd.models;

/**
 * Created by Shipon on 9/24/2018.
 */

public class Renter {
    private String renterName;
    private String renterContact;
    private String renterOccupation;
    private String renterMonthlyIncome;
    private String renterPassword;
    private String renterStatus;

    public Renter(String renterName, String renterContact, String renterOccupation,
                  String renterMonthlyIncome, String renterPassword, String renterStatus) {
        this.renterName = renterName;
        this.renterContact = renterContact;
        this.renterOccupation = renterOccupation;
        this.renterMonthlyIncome = renterMonthlyIncome;
        this.renterPassword = renterPassword;
        this.renterStatus = renterStatus;
    }
    public String getRenterName() {
        return renterName;
    }

    public String getRenterContact() {
        return renterContact;
    }

    public String getRenterOccupation() {
        return renterOccupation;
    }

    public String getRenterMonthlyIncome() {
        return renterMonthlyIncome;
    }

    public String getRenterPassword() {
        return renterPassword;
    }

    public String getRenterStatus() {
        return renterStatus;
    }










}
