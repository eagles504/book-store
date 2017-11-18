package com.xurui.bookstore.dao;

public class BookInstanceEntity {
    public BookInstanceEntity(int bookId, String barCode, int bookStoreId, int status) {
        this.bookId = bookId;
        this.barCode = barCode;
        this.bookStoreId = bookStoreId;
        this.status = status;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getBookStoreId() {
        return bookStoreId;
    }

    public void setBookStoreId(int bookStoreId) {
        this.bookStoreId = bookStoreId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookInstanceEntity that = (BookInstanceEntity) o;

        if (bookStoreId != that.bookStoreId) return false;
        if (status != that.status) return false;
        return barCode.equals(that.barCode);
    }

    @Override
    public int hashCode() {
        int result = barCode.hashCode();
        result = 31 * result + bookStoreId;
        result = 31 * result + status;
        return result;
    }

    private int bookId;
    private String barCode;
    private int bookStoreId;
    private int status;
}
