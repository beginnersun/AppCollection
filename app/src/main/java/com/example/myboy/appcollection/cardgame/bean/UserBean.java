package com.example.myboy.appcollection.cardgame.bean;


public class UserBean {

    private String userName;

    private String password;

    private String phone;

    public UserBean(String userName, String password, String phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }

    public UserBean() {
        this("管理员","admin","15280593883");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

}
