package com.example.contacts;

public class RegisterModel {

    String name;
    String Address;
    String mobile;
    String Email;
    String password;
    String cpassword;
    String birthdate;

    public RegisterModel() {

    }

    public RegisterModel(String name, String address, String mobile, String email, String password, String cpassword, String birthdate) {
        this.name = name;
        Address = address;
        this.mobile = mobile;
        Email = email;
        this.password = password;
        this.cpassword = cpassword;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
