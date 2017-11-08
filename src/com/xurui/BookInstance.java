package com.xurui;

import java.io.Serializable;

public class BookInstance  implements Serializable{
    private static final long serialVersionUID = -8490904922224877410L;

    public BookInstance(int definitionId, String barCode) {
        this.definitionId = definitionId;
        this.barCode = barCode;
    }

    public int getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(int definitionId) {
        this.definitionId = definitionId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private int definitionId;
    private String barCode;
}
