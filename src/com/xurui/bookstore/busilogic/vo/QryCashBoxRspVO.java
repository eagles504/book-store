package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.dao.CashBoxEntity;

public class QryCashBoxRspVO {
    public CashBoxEntity getCashBox() {
        return cashBox;
    }

    public void setCashBox(CashBoxEntity cashBox) {
        this.cashBox = cashBox;
    }

    public QryCashBoxRspVO(CashBoxEntity cashBox) {

        this.cashBox = cashBox;
    }

    private CashBoxEntity cashBox;
}
