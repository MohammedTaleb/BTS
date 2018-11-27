package com.example.ma.bts;

/**
 * Created by BTS on 11/24/18.
 */

public class BusInfo {

    String busNumber,manufacturerCompany,busModel,capacity,busType,image,busDriverId;

    public BusInfo() {
    }

    public BusInfo(String busNumber, String manufacturerCompany, String busModel, String capacity, String busType,String image,String busDriverId) {

        this.busNumber = busNumber;
        this.manufacturerCompany = manufacturerCompany;
        this.busModel = busModel;
        this.capacity = capacity;
        this.busType = busType;
        this.image = image;
        this.busDriverId = busDriverId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getManufacturerCompany() {
        return manufacturerCompany;
    }

    public void setManufacturerCompany(String manufacturerCompany) {
        this.manufacturerCompany = manufacturerCompany;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBusDriverId() {
        return busDriverId;
    }

    public void setBusDriverId(String busDriverId) {
        this.busDriverId = busDriverId;
    }
}
