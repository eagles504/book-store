package com.xurui.bookstore.busilogic.vo;

import java.util.Date;

public class QryPaymentLogReqVO {
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getCashBoxId() {
        return cashBoxId;
    }

    public void setCashBoxId(int cashBoxId) {
        this.cashBoxId = cashBoxId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public QryPaymentLogReqVO(int logId, int storeId, int cashBoxId, String employee, String customer, Date date) {

        this.logId = logId;
        this.storeId = storeId;
        this.cashBoxId = cashBoxId;
        this.employee = employee;
        this.customer = customer;
        this.date = date;
    }

    private int logId;
    private int storeId;
    private int cashBoxId;
    private String employee;
    private String customer;
    private Date date;
}
