package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.dao.BookStoreEntity;

import java.util.List;

public class QryBookStoreRspVO {
    public List<BookStoreEntity> getStores() {
        return stores;
    }

    public void setStores(List<BookStoreEntity> stores) {
        this.stores = stores;
    }

    public QryBookStoreRspVO(List<BookStoreEntity> stores) {

        this.stores = stores;
    }

    private List<BookStoreEntity> stores;
}
