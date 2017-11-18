package com.xurui.bookstore.busilogic.vo;

public class QryBookStoreReqVO {
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public QryBookStoreReqVO(int storeId, String storeName, String addr) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.addr = addr;
    }

    private int storeId;
    private String storeName;
    private String addr;
}
