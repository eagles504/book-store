package com.xurui;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookDefinition implements Serializable{
    private static final long serialVersionUID = -6745080250009547817L;

    BookDefinition(String name, String author, String publisher, int version,
                          Date publishDate, String ISBN, int definitionId, double price, BookLocation location) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.version = version;
        this.publishDate = publishDate;
        this.ISBN = ISBN;
        this.definitionId = definitionId;
        this.price = price;
        this.location = location;
        booksNum = 0;
    }

    void displayInfo() {
        //System.out.println("Book Definition Id: " + definitionId);
        System.out.println("书名：" + name);
        System.out.println("作者：" + author);
        System.out.println("出版社：" + publisher);
        System.out.println("版本：" + version);
        System.out.println("出版日期：" + new SimpleDateFormat("yyyy-MM-dd").format(publishDate));
        System.out.println("ISBN：" + ISBN);
        System.out.println("定价：" + price);
        System.out.println("书架位置: " + location.getSelfSn() + "号书架" + location.getLineNum() + "排");
        System.out.println("该书籍剩余数量：" + booksNum);
    }

    int getDefinitionId() {
        return definitionId;
    }

    void setBooksNum(int booksNum) {
        this.booksNum = booksNum;
    }

    int getBooksNum() {
        return booksNum;
    }

    String getName() {
        return name;
    }

    String getAuthor() {
        return author;
    }

    String getPublisher() {
        return publisher;
    }

    int getVersion() {
        return version;
    }

    double getPrice() {
        return price;
    }

    void setLocation(BookLocation location) {
        this.location = location;
    }

    void setPrice(double price) {
        this.price = price;
    }

    private String name;
    private String author;
    private String publisher;
    private int version;
    private Date publishDate;
    private String ISBN;
    private double price;
    private BookLocation location;
    private int definitionId;
    private int booksNum;
}
