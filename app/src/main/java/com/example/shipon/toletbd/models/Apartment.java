package com.example.shipon.toletbd.models;

/**
 * Created by Shipon on 9/20/2018.
 */

public class Apartment {

    public Apartment(String nameOwner, String contactOwner, String districtOwner, String areaOwner, String homeCatagory,
                     String fromMonth, String detailsOwnerHome, int costHome) {
        this.nameOwner = nameOwner;
        this.contactOwner = contactOwner;
        this.districtOwner = districtOwner;
        this.areaOwner = areaOwner;
        this.homeCatagory = homeCatagory;
        this.fromMonth = fromMonth;
        this.costHome = costHome;
        this.detailsOwnerHome = detailsOwnerHome;
    }

    private String nameOwner;
    private String contactOwner;
    private String districtOwner;
    private String areaOwner;
    private String homeCatagory;
    private  String fromMonth;
    private int costHome;
    private String detailsOwnerHome;




}
