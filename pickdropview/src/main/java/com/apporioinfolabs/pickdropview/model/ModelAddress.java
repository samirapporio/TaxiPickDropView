package com.apporioinfolabs.pickdropview.model;

public class ModelAddress {
    public String Address ;
    public double latitude ;
    public double longitude ;

    public ModelAddress(String address, double latitude, double longitude) {
        Address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
