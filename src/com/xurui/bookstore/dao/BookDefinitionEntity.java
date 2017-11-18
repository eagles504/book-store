package com.xurui.bookstore.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookDefinitionEntity{
    public BookDefinitionEntity(int id, String name, String author, String publisher,
                                int version, Date publishDate, String ISBN, double price,
                                int shelfNo, int shelfRow, int bookCount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.version = version;
        this.publishDate = publishDate;
        this.ISBN = ISBN;
        this.price = price;
        this.shelfNo = shelfNo;
        this.shelfRow = shelfRow;
        this.bookCount = bookCount;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(int shelfNo) {
        this.shelfNo = shelfNo;
    }

    public int getShelfRow() {
        return shelfRow;
    }

    public void setShelfRow(int shelfRow) {
        this.shelfRow = shelfRow;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookDefinitionEntity that = (BookDefinitionEntity) o;

        if (version != that.version) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (shelfNo != that.shelfNo) return false;
        if (shelfRow != that.shelfRow) return false;
        if (bookCount != that.bookCount) return false;
        if (!name.equals(that.name)) return false;
        if (!author.equals(that.author)) return false;
        if (!publisher.equals(that.publisher)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!sdf.format(publishDate).equals(sdf.format(that.publishDate))) return false;
        return ISBN.equals(that.ISBN);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + publisher.hashCode();
        result = 31 * result + version;
        result = 31 * result + publishDate.hashCode();
        result = 31 * result + ISBN.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + shelfNo;
        result = 31 * result + shelfRow;
        result = 31 * result + bookCount;
        return result;
    }

    private int id;
    private String name;
    private String author;
    private String publisher;
    private int version;
    private Date publishDate;
    private String ISBN;
    private double price;
    private int shelfNo;
    private int shelfRow;
    private int bookCount;
}
