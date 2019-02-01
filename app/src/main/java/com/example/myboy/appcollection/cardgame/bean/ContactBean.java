package com.example.myboy.appcollection.cardgame.bean;

public class ContactBean {

    private String img;
    private String phone;
    private String name;
    private boolean registered;

    public ContactBean() {
        this("","","",false);
    }

    public ContactBean(String img, String phone, String name, boolean registered) {
        this.img = img;
        this.phone = phone;
        this.name = name;
        this.registered = registered;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
