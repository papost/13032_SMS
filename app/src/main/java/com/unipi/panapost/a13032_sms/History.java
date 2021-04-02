package com.unipi.panapost.a13032_sms;

import java.io.Serializable;

public class History implements Serializable {
    String date;

    public History(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
