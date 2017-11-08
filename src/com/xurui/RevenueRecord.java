package com.xurui;

import java.io.Serializable;
import java.util.Date;

public class RevenueRecord implements Serializable{
    private static final long serialVersionUID = -136198683474856968L;
    private double incomeMoney;
    private double change;
    private String employeeName;
    private Date date;

    public RevenueRecord(double incomeMoney, double change, String employeeName,
                         Date date) {
        this.incomeMoney = incomeMoney;
        this.change = change;
        this.employeeName = employeeName;
        this.date = date;
    }

    public void getInfo() {
        System.out.println("[" + date + "]" + incomeMoney + " - " + change + " - " + employeeName);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Date getDate() {
        return date;
    }
}
