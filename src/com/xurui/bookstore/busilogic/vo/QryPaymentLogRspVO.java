package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.dao.PaymentLogEntity;

import java.util.List;

public class QryPaymentLogRspVO {
    public List<PaymentLogEntity> getLogs() {
        return logs;
    }

    public void setLogs(List<PaymentLogEntity> logs) {
        this.logs = logs;
    }

    public QryPaymentLogRspVO(List<PaymentLogEntity> logs) {

        this.logs = logs;
    }

    private List<PaymentLogEntity> logs;
}
