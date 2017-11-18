package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.dao.BookInstanceEntity;

public class QryBookRspVO {
    public QryBookRspVO(BookInstanceEntity inst) {
        this.inst = inst;
    }

    public BookInstanceEntity getInst() {
        return inst;
    }

    public void setInst(BookInstanceEntity inst) {
        this.inst = inst;
    }

    private BookInstanceEntity inst;
}
