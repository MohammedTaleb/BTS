package com.example.ma.bts;

/**
 * Created by BTS on 10/31/18.
 */

public class DriverInfo {
    String name,busId,age,email,nationality,number,address,image;

    public DriverInfo(String name, String busId, String age, String email, String nationality, String number ,String address,String image) {
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
