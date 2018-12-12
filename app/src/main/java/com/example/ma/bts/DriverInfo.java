package com.example.ma.bts;

/**
 * Created by BTS on 10/31/18.
 */

public class DriverInfo {
    String name,busId,age,email,nationality,number,address,image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DriverInfo(String name, String busId, String age, String email, String nationality, String number , String address, String image) {
        this.name = name;
        this.busId = busId;
        this.age = age;
        this.email = email;
        this.nationality = nationality;
        this.number = number;
        this.address = address;
        this.image = image;
    }

    public DriverInfo() {
    }
}
