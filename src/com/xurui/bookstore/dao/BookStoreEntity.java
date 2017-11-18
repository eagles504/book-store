package com.xurui.bookstore.dao;

public class BookStoreEntity {
    public BookStoreEntity(int id, String name, String addr, Boolean onlineFlag, int status) {
        this.id = id;
        this.name = name;
        this.addr = addr;
        this.onlineFlag = onlineFlag;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Boolean getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(Boolean onlineFlag) {
        this.onlineFlag = onlineFlag;
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

        BookStoreEntity that = (BookStoreEntity) o;

        if (status != that.status) return false;
        if (!name.equals(that.name)) return false;
        if (!addr.equals(that.addr)) return false;
        return onlineFlag.equals(that.onlineFlag);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + addr.hashCode();
        result = 31 * result + onlineFlag.hashCode();
        result = 31 * result + status;
        return result;
    }

    private int id;
    private String name;
    private String addr;
    private Boolean onlineFlag;
    private int status;
}
