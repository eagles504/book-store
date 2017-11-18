package com.xurui.bookstore.busilogic.vo;

public class InboundReqVO {
    public InboundReqVO(int bookDefId, String barCode, int storeId) {
        this.bookDefId = bookDefId;
        this.barCode = barCode;
        this.storeId = storeId;
    }

    public int getStoreId() {

        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getBookDefId() {
        return bookDefId;
    }

    public void setBookDefId(int bookDefId) {
        this.bookDefId = bookDefId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private int bookDefId;
    private String barCode;
    private int storeId;
}
