package com.xurui.bookstore.busilogic.vo;

import com.xurui.bookstore.dao.BookDefinitionEntity;

import java.util.List;

public class QryBookDefRspVO {
    public QryBookDefRspVO(List<BookDefinitionEntity> bookDefinitions) {
        this.bookDefinitions = bookDefinitions;
    }

    public List<BookDefinitionEntity> getBookDefinitions() {
        return bookDefinitions;
    }

    public void setBookDefinitions(List<BookDefinitionEntity> bookDefinitions) {
        this.bookDefinitions = bookDefinitions;
    }

    private List<BookDefinitionEntity> bookDefinitions;
}
