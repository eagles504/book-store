package com.xurui.bookstore.busilogic.vo;

public class CalcFeeReqVO {
    public CalcFeeReqVO(String[] barCodes) {
        this.barCodes = barCodes;
    }

    public String[] getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(String[] barCodes) {
        this.barCodes = barCodes;
    }

    private String[] barCodes;
}
