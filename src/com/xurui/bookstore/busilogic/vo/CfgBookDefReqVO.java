package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.Constants.Constants;
import com.xurui.bookstore.dao.BookDefinitionEntity;

public class CfgBookDefReqVO {
    public CfgBookDefReqVO(BookDefinitionEntity bookDefinition, Constants.Operation oper) {
        this.bookDefinition = bookDefinition;
        this.oper = oper;
    }

    public BookDefinitionEntity getBookDefinition() {
        return bookDefinition;
    }

    public void setBookDefinition(BookDefinitionEntity bookDefinition) {
        this.bookDefinition = bookDefinition;
    }


    public Constants.Operation getOper() {
        return oper;
    }

    public void setOper(Constants.Operation oper) {
        this.oper = oper;
    }

    private BookDefinitionEntity bookDefinition;    // for update/new, bookDefinition used
    private Constants.Operation oper;
}
