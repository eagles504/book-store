package com.xurui.bookstore.busilogic;

import com.xurui.bookstore.Constants.Constants;
import com.xurui.bookstore.busilogic.vo.*;
import com.xurui.bookstore.dao.*;
import com.xurui.bookstore.exception.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.List;

public class BookStoreMgmt {
    public static void cfgBookStore(@NotNull CfgBookStoreReqVO req) throws BookStoreException {
        if(req.getOper() == null) {
            throw new BookStoreException(Constants.ExceptionMsg.OPER_NULL.getName(),
                    Constants.ExceptionMsg.OPER_NULL.getValue());
        }

        if(req.getOper() == Constants.Operation.MOD ||
                req.getOper() == Constants.Operation.DEL) {
            QryBookStoreReqVO qryReq = new QryBookStoreReqVO(req.getStore().getId(), null, null);
            QryBookStoreRspVO qryRsp = qryBookStore(qryReq);
            if(qryRsp.getStores() == null || qryRsp.getStores().isEmpty()) {
                throw new BookStoreException(Constants.ExceptionMsg.DATA_NOT_EXIST.getName(),
                        Constants.ExceptionMsg.DATA_NOT_EXIST.getValue());
            }
        }

        MySQLDAO inst = MySQLDAO.getInstance();
        Connection conn = inst.getConnection();
        inst.updateBookStore(conn, req.getStore(), req.getOper());

        if(req.getOper() == Constants.Operation.ADD) {
            QryBookStoreReqVO qryStoreReq = new QryBookStoreReqVO(0, req.getStore().getName(),
                    req.getStore().getAddr());
            QryBookStoreRspVO qryStoreRsp = qryBookStore(qryStoreReq);
            CashBoxEntity cashBox = new CashBoxEntity(0, qryStoreRsp.getStores().get(0).getId(), 0, 1);
            inst.updateCashBox(conn, cashBox, req.getOper());
        }
    }

    public static QryBookStoreRspVO qryBookStore(@NotNull QryBookStoreReqVO req) throws BookStoreException {
        MySQLDAO inst = MySQLDAO.getInstance();
        List<BookStoreEntity> list =
                inst.qryBookStore(inst.getConnection(), req.getStoreId(), req.getStoreName(), req.getAddr());
        return new QryBookStoreRspVO(list);
    }

    public static QryBookDefRspVO qryBookDef(@NotNull QryBookDefReqVO req) throws BookStoreException {
        MySQLDAO inst = MySQLDAO.getInstance();
        List<BookDefinitionEntity> list =
                inst.qryBookDef(inst.getConnection(), req.getBookId(), req.getBookName(), req.getAuthorName());
        return new QryBookDefRspVO(list);
    }

    public static void cfgBookDef(@NotNull CfgBookDefReqVO req) throws BookStoreException {
        if(req.getOper() == null) {
            throw new BookStoreException(Constants.ExceptionMsg.OPER_NULL.getName(),
                    Constants.ExceptionMsg.OPER_NULL.getValue());
        }

        if(req.getOper() == Constants.Operation.MOD ||
                req.getOper() == Constants.Operation.DEL) {
            QryBookDefReqVO qryReq = new QryBookDefReqVO(req.getBookDefinition().getId(),
                    null, null);
            QryBookDefRspVO qryRsp = qryBookDef(qryReq);
            if(qryRsp.getBookDefinitions() == null || qryRsp.getBookDefinitions().isEmpty()) {
                throw new BookStoreException(Constants.ExceptionMsg.DATA_NOT_EXIST.getName(),
                        Constants.ExceptionMsg.DATA_NOT_EXIST.getValue());
            }
        }

        MySQLDAO inst = MySQLDAO.getInstance();
        inst.updateBookDef(inst.getConnection(), req.getBookDefinition(), req.getOper());
    }

    public static void inbound(@NotNull InboundReqVO req) throws BookStoreException {
        QryBookStoreReqVO qryStoreReq = new QryBookStoreReqVO(req.getStoreId(), null, null);
        QryBookStoreRspVO qryStoreRsp = qryBookStore(qryStoreReq);
        if(qryStoreRsp.getStores() == null || qryStoreRsp.getStores().isEmpty()) {
            throw new BookStoreException(Constants.ExceptionMsg.BOOK_STORE_NOT_EXIST.getName(),
                    Constants.ExceptionMsg.BOOK_STORE_NOT_EXIST.getValue());
        }

        QryBookDefReqVO qryDefReq = new QryBookDefReqVO(req.getBookDefId(), null, null);
        QryBookDefRspVO qryDefRsp = qryBookDef(qryDefReq);
        if(qryDefRsp.getBookDefinitions() == null || qryDefRsp.getBookDefinitions().isEmpty()) {
            throw new BookStoreException(Constants.ExceptionMsg.BOOK_DEF_NOT_EXIST.getName(),
                    Constants.ExceptionMsg.BOOK_DEF_NOT_EXIST.getValue());
        }

        MySQLDAO inst = MySQLDAO.getInstance();
        BookInstanceEntity bookInst = new BookInstanceEntity(req.getBookDefId(),
                req.getBarCode(), req.getStoreId(), 1);
        inst.updateBookInst(inst.getConnection(), bookInst, Constants.Operation.ADD);
    }

    public static QryBookRspVO qryBook(@NotNull QryBookReqVO req) throws BookStoreException  {
        if(req.getBarCode() == null || req.getBarCode().isEmpty()) {
            throw new BookStoreException(Constants.ExceptionMsg.BAR_CODE_INVALID.getName(),
                    Constants.ExceptionMsg.BAR_CODE_INVALID.getValue());
        }

        MySQLDAO inst = MySQLDAO.getInstance();
        BookInstanceEntity bookInst = inst.qryBookInst(inst.getConnection(), req.getBarCode());
        return new QryBookRspVO(bookInst);
    }

    public static CalcFeeRspVO calcFee(@NotNull CalcFeeReqVO req) throws BookStoreException {
        if(req.getBarCodes() == null || req.getBarCodes().length == 0) {
            throw new BookStoreException(Constants.ExceptionMsg.BAR_CODE_INVALID.getName(),
                    Constants.ExceptionMsg.BAR_CODE_INVALID.getValue());
        }

        CalcFeeRspVO rsp = new CalcFeeRspVO(0);
        double fee = 0;
        QryBookDefReqVO qryDefReq = new QryBookDefReqVO(0, null, null);
        QryBookDefRspVO qryDefRsp;
        QryBookReqVO qryBookReq = new QryBookReqVO(null);
        QryBookRspVO qryBookRsp;
        for(String barCode : req.getBarCodes()) {
            qryBookReq.setBarCode(barCode);
            qryBookRsp = qryBook(qryBookReq);
            if(qryBookRsp.getInst() == null) {
                throw new BookStoreException(Constants.ExceptionMsg.BOOK_INST_NOT_EXIST.getName(),
                        Constants.ExceptionMsg.BOOK_INST_NOT_EXIST.getValue());
            }

            qryDefReq.setBookId(qryBookRsp.getInst().getBookId());
            qryDefRsp = qryBookDef(qryDefReq);
            fee += qryDefRsp.getBookDefinitions().get(0).getPrice();
        }

        rsp.setFee(fee);
        return rsp;
    }

    public static SellBookRspVO sellBook(@NotNull SellBookReqVO req) throws BookStoreException {
        CalcFeeReqVO calcReq = new CalcFeeReqVO(req.getBarCodes());
        CalcFeeRspVO calcRsp = calcFee(calcReq);
        if(req.getIncome() < calcRsp.getFee()) {
            throw new BookStoreException(Constants.ExceptionMsg.INCOME_MONEY_INVALID.getName(),
                    Constants.ExceptionMsg.INCOME_MONEY_INVALID.getValue());
        }

        QryBookStoreReqVO qryStoreReq = new QryBookStoreReqVO(req.getStoreId(), null, null);
        QryBookStoreRspVO qryStoreRsp = qryBookStore(qryStoreReq);
        if(qryStoreRsp.getStores() == null || qryStoreRsp.getStores().isEmpty()) {
            throw new BookStoreException(Constants.ExceptionMsg.BOOK_STORE_NOT_EXIST.getName(),
                    Constants.ExceptionMsg.BOOK_STORE_NOT_EXIST.getValue());
        }

        QryBookReqVO qryBookReq = new QryBookReqVO(null);
        QryBookRspVO qryBookRsp;
        QryBookDefReqVO qryDefReq = new QryBookDefReqVO(0, null, null);
        QryBookDefRspVO qryDefRsp;
        MySQLDAO inst = MySQLDAO.getInstance();
        Connection conn = inst.getConnection();

        /* 验证输入是否有已售出的书籍 */
        for(String barCode : req.getBarCodes()) {
            qryBookReq.setBarCode(barCode);
            qryBookRsp = qryBook(qryBookReq);
            if(qryBookRsp.getInst().getStatus() == 2) {
                throw new BookStoreException(Constants.ExceptionMsg.BOOK_INST_SOLD.getName(),
                        String.format(Constants.ExceptionMsg.BOOK_INST_SOLD.getValue(), barCode));
            }
        }

        /* 更新为已售出 */
        for(String barCode : req.getBarCodes()) {
            qryBookReq.setBarCode(barCode);
            qryBookRsp = qryBook(qryBookReq);

            qryBookRsp.getInst().setStatus(2);
            inst.updateBookInst(conn, qryBookRsp.getInst(), Constants.Operation.MOD);
        }

        /* 记录交易记录 */
        QryCashBoxReqVO qryBoxReq = new QryCashBoxReqVO(req.getStoreId());
        QryCashBoxRspVO qryBoxRsp = qryCashBox(qryBoxReq);
        PaymentLogEntity log = new PaymentLogEntity(0, qryBoxRsp.getCashBox().getId(), req.getIncome(),
                req.getIncome() - calcRsp.getFee(), req.getEmployee(), req.getCustomer(), req.getSellDate());
        inst.updatePaymentLog(conn, log, Constants.Operation.ADD);

        SellBookRspVO rsp = new SellBookRspVO(req.getIncome() - calcRsp.getFee());
        return rsp;
    }

    public static QryPaymentLogRspVO qryPaymentLog(@NotNull QryPaymentLogReqVO req) throws BookStoreException  {
        if(req.getStoreId() != 0 && req.getCashBoxId() == 0) {
            QryCashBoxReqVO qryBoxReq = new QryCashBoxReqVO(req.getStoreId());
            QryCashBoxRspVO qryBoxRsp = qryCashBox(qryBoxReq);
            if(qryBoxRsp.getCashBox() == null) {
                throw new BookStoreException(Constants.ExceptionMsg.BOOK_STORE_NOT_EXIST.getName(),
                        Constants.ExceptionMsg.BOOK_STORE_NOT_EXIST.getValue());
            }

            req.setCashBoxId(qryBoxRsp.getCashBox().getId());
        }
        MySQLDAO inst = MySQLDAO.getInstance();
        List<PaymentLogEntity> list = inst.qryPaymentLog(inst.getConnection(), req.getLogId(), req.getCashBoxId(), req.getEmployee(),
                req.getCustomer(), req.getDate());
        return new QryPaymentLogRspVO(list);
    }

    public static QryCashBoxRspVO qryCashBox(@NotNull QryCashBoxReqVO req) throws BookStoreException  {
        QryCashBoxRspVO rsp = new QryCashBoxRspVO(null);

        MySQLDAO inst = MySQLDAO.getInstance();
        List<CashBoxEntity> list = inst.qryCashBox(inst.getConnection(), 0, req.getStoreId());
        if(list != null && !list.isEmpty()) {
            rsp.setCashBox(list.get(0));
        }

        return rsp;
    }
}