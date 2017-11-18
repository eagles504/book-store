package com.xurui.bookstore.dao;

public class CashBoxEntity {
    public CashBoxEntity(int id, int bookStoreId, double balance, int status) {
        this.id = id;
        this.bookStoreId = bookStoreId;
        this.balance = balance;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookStoreId() {
        return bookStoreId;
    }

    public void setBookStoreId(int bookStoreId) {
        this.bookStoreId = bookStoreId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashBoxEntity that = (CashBoxEntity) o;

        if (bookStoreId != that.bookStoreId) return false;
        if (Double.compare(that.balance, balance) != 0) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result =  bookStoreId;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + status;
        return result;
    }

    private int id;
    private int bookStoreId;
    private double balance;
    private int status;
}
