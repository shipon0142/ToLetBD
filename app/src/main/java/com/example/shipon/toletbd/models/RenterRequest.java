package com.example.shipon.toletbd.models;

/**
 * Created by Shipon on 9/28/2018.
 */

public class RenterRequest {
    private String name;

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getRequest() {
        return request;
    }

    private String contact;
    private String request;

    public String getKey() {
        return key;
    }

    private String key;
    public RenterRequest(String name, String contact, String request) {
        this.name = name;
        this.contact = contact;
        this.request = request;
    }
    public RenterRequest(String name, String contact, String request,String key) {
        this.name = name;
        this.contact = contact;
        this.request = request;
        this.key=key;
    }


}
