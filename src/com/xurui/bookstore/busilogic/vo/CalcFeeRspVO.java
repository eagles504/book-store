package com.xurui.bookstore.busilogic.vo;

public class CalcFeeRspVO {
    public CalcFeeRspVO(double fee) {
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    private double fee;
}
