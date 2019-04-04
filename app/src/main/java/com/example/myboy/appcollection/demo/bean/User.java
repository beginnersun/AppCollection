package com.example.myboy.appcollection.demo.bean;

public class User {

    private String username;
    private String password;
    private String phone;
    private String img;
    private String info;

    public User(String username, String password, String phone, String img, String info) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.img = img;
        this.info = info;
    }

    public User() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", img='" + img + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
