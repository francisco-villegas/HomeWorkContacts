package com.example.francisco.homeworkcontacts;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

public class MyContact {
    String name, last_name, phone, email, company, address;
    byte[] bitmap;

    int id;

    public void setID(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {

        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public int getID() {
        return id;
    }

    public MyContact(int id, String name, String last_name, String phone, String email, String company, String address, byte[] bitmap) {

        this.name = name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.address = address;
        this.bitmap = bitmap;
        this.id = id;
    }
}
