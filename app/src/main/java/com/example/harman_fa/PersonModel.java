package com.example.harman_fa;

import java.io.Serializable;

public class PersonModel implements Serializable {
    private int id;
    private String fName, lName, address, phone;

    public PersonModel(int id, String fName, String lName, String phone, String address) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.address = address;

    }

    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
