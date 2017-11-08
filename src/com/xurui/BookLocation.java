package com.xurui;

import java.io.Serializable;

public class BookLocation implements Serializable {
    private static final long serialVersionUID = -6551817161359047260L;

    public BookLocation(int selfSn, int lineNum) {
        this.selfSn = selfSn;
        this.lineNum = lineNum;
    }

    public int getSelfSn() {
        return selfSn;
    }

    public void setSelfSn(int selfSn) {
        this.selfSn = selfSn;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    private int selfSn;
    private int lineNum;
}
