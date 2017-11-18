package com.xurui.bookstore.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentLogEntity {
    public PaymentLogEntity(int id, int cashBoxId, double income, double expense, String employee, String customer, Date date) {
        this.id = id;
        this.cashBoxId = cashBoxId;
        this.income = income;
        this.expense = expense;
        this.employee = employee;
        this.customer = customer;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCashBoxId() {
        return cashBoxId;
    }

    public void setCashBoxId(int cashBoxId) {
        this.cashBoxId = cashBoxId;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentLogEntity that = (PaymentLogEntity) o;

        if (cashBoxId != that.cashBoxId) return false;
        if (Double.compare(that.income, income) != 0) return false;
        if (Double.compare(that.expense, expense) != 0) return false;
        if (!employee.equals(that.employee)) return false;
        if (!customer.equals(that.customer)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date).equals(sdf.format(that.date));
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = cashBoxId;
        temp = Double.doubleToLongBits(income);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(expense);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + employee.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    private int id;
    private int cashBoxId;
    private double income;
    private double expense;
    private String employee;
    private String customer;
    private Date date;
}
