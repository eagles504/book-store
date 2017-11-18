package com.xurui.bookstore.busilogic.vo;

public class QryBookReqVO {
    public QryBookReqVO(String barCode) {
        this.barCode = barCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private String barCode;
}
