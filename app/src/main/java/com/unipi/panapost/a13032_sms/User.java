package com.unipi.panapost.a13032_sms;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username, email, address;
    private String app_theme;
    private String history;

    public User(String username, String email, String address, String app_theme) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.app_theme = app_theme;
    }

    public User(String username, String email, String address, String app_theme, String history) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.app_theme = app_theme;
        this.history = history;
    }

    public User() {
    }

    public User(String username) {
        this.username = username;

    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getApp_theme() {
        return app_theme;
    }
}
