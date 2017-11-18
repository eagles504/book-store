package com.xurui.bookstore.busilogic.vo;

public class QryCashBoxReqVO {
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public QryCashBoxReqVO(int storeId) {

        this.storeId = storeId;
    }

    private int storeId;
}
