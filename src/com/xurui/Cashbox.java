package com.xurui;

import java.io.Serializable;
import java.util.Date;

public class Cashbox implements Serializable{
    private static final long serialVersionUID = 1667818584218525509L;
    private double money;
    private RevenueRecord [] revenueRecords;
    private int recordCount;

    public Cashbox() {
        money = 0.0;
        revenueRecords = new RevenueRecord[1000]; // 待优化
    }

    public Cashbox(double money) {
        this();
        this.money = money;
    }

    public void makeMoney(double money, double change, String employeeName) {
        this.money += money - change;
        this.revenueRecords[recordCount++] = new RevenueRecord(money,change,employeeName, new Date());
    }

    public double getMoney() {
        return money;
    }

    public RevenueRecord[] getRevenueRecords() {
        RevenueRecord [] todayRecords = new RevenueRecord[100];
        int index = 0;
        int millisPerDay = 1000*3600*24;
        Date today = new Date(System.currentTimeMillis() / millisPerDay * millisPerDay);
        for(int i=0; i < this.revenueRecords.length; i++) {
            if(revenueRecords[i].getDate().after(today) && revenueRecords[i].getDate().getTime() - today.getTime() < millisPerDay) {
                todayRecords[index++] = revenueRecords[i];
            }
        }

        return todayRecords;
    }

    // 待后续补充优化
    public RevenueRecord[] searchRevenueRecord(String employeeName, Date startDate, Date endDate) {
        return null;
    }
}
