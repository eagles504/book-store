package com.xurui.bookstore.busilogic.vo;

import java.util.Date;

public class SellBookReqVO {
    public SellBookReqVO(double income, String employee, String customer, String[] barCodes, Date sellDate, int storeId) {
        this.income = income;
        this.employee = employee;
        this.customer = customer;
        this.barCodes = barCodes;
        this.sellDate = sellDate;
        this.storeId = storeId;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String[] getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(String[] barCodes) {
        this.barCodes = barCodes;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    private double income;
    private String employee;
    private String customer;
    private String[] barCodes;
    private Date sellDate;
    private int storeId;
}
