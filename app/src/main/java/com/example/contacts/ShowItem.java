package com.example.contacts;

public class ShowItem {

    String id;
    String path;
    String name;
    String number;
    String address;
    String uid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ShowItem(String id, String path, String name, String number, String address, String uid) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.number = number;
        this.address = address;
        this.uid = uid;
    }
}
