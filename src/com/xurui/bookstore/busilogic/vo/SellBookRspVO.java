package com.xurui.bookstore.busilogic.vo;

public class SellBookRspVO {
    public SellBookRspVO(double expense) {
        this.expense = expense;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    private double expense;
}
