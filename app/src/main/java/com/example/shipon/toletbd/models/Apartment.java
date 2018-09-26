package com.example.shipon.toletbd.models;

/**
 * Created by Shipon on 9/20/2018.
 */

public class Apartment {

    public Apartment(String nameOwner, String contactOwner, String districtOwner, String areaOwner, String homeCatagory,
                     String fromMonth, String detailsOwnerHome, String houseNo, String costHome) {
        this.nameOwner = nameOwner;
        this.contactOwner = contactOwner;
        this.districtOwner = districtOwner;
        this.areaOwner = areaOwner;
        this.homeCatagory = homeCatagory;
        this.fromMonth = fromMonth;
        this.costHome = costHome;
        this.detailsOwnerHome = detailsOwnerHome;
        this.houseNo=houseNo;
    }

    private String nameOwner;
    private String contactOwner;
    private String districtOwner;
    private String areaOwner;

    public String getNameOwner() {
        return nameOwner;
    }

    public String getContactOwner() {
        return contactOwner;
    }

    public String getDistrictOwner() {
        return districtOwner;
    }

    public String getAreaOwner() {
        return areaOwner;
    }

    public String getHomeCatagory() {
        return homeCatagory;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public String getCostHome() {
        return costHome;
    }

    public String getDetailsOwnerHome() {
        return detailsOwnerHome;
    }

    public String getHouseNo() {
        return houseNo;
    }

    private String homeCatagory;
    private  String fromMonth;
    private String costHome;
    private String detailsOwnerHome;
    private String houseNo;




}
