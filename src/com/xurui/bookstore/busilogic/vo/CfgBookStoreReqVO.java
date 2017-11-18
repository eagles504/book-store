package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.Constants.Constants;
import com.xurui.bookstore.dao.BookStoreEntity;

public class CfgBookStoreReqVO {
    public BookStoreEntity getStore() {
        return store;
    }

    public void setStore(BookStoreEntity store) {
        this.store = store;
    }

    public Constants.Operation getOper() {
        return oper;
    }

    public void setOper(Constants.Operation oper) {
        this.oper = oper;
    }

    public CfgBookStoreReqVO(BookStoreEntity store, Constants.Operation oper) {

        this.store = store;
        this.oper = oper;
    }

    private BookStoreEntity store;
    private Constants.Operation oper;
}
