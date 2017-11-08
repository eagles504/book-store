package com.xurui;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class BookStore implements Serializable {
    private static final long serialVersionUID = 6631276957815473326L;

    BookStore() {
        bookDefinitions = new BookDefinition[MaxBookDefNum];
        books = new BookInstance[MaxBookNum];
        curBookNum = 0;
        curBookDefNum = 0;
    }

    // bookName authorName publisher version publishDate ISBN price location.selfSN location.lineNum
     boolean defineBook(String [] args){
        if(validateBookDefinition(args)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                BookDefinition bd = new BookDefinition(args[0], args[1], args[2], Integer.parseInt(args[3]), sdf.parse(args[4]),
                        args[5], curBookDefNum+1, Double.parseDouble(args[6]), new BookLocation(Integer.parseInt(args[7]), Integer.parseInt(args[8])));
                bookDefinitions[curBookDefNum++] = bd;
            }catch(ParseException e){
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    void inbound(BookInstance[] books, int inboundBookCount){
        if(inboundBookCount + curBookNum >= MaxBookNum) {
           System.out.println("入库后书籍数目将超出书店书籍数目最大值：" + MaxBookNum);
           return;
        }
        for(int i=0; i < curBookDefNum; i++) {
            if(bookDefinitions[i].getDefinitionId() == books[0].getDefinitionId()) {
                bookDefinitions[i].setBooksNum(bookDefinitions[i].getBooksNum() + inboundBookCount);
                break;
            }
        }

        for(int i=0; i < inboundBookCount; i++) {
            this.books[curBookNum++] = books[i];
        }
    }

    // bookName authorName publisher version
    BookDefinition [] searchBooks(String [] args) {
        try {
            if (validateBookSearch(args)) {
                BookDefinition[] rslt = new BookDefinition[curBookDefNum];
                String bookName = args[0];
                String authorName = args.length > 1 ? args[1] : null;
                String publisher = args.length > 2 ? args[2] : null;
                int version = args.length > 3 ? Integer.parseInt(args[3]) : 0;
                int rsltIndex = 0;
                for (int i = 0; i < curBookDefNum; i++) {
                    if (bookDefinitions[i].getName().equalsIgnoreCase(bookName) &&
                            (authorName == null || bookDefinitions[i].getAuthor().equalsIgnoreCase(authorName)) &&
                            (publisher == null || bookDefinitions[i].getPublisher().equalsIgnoreCase(publisher)) &&
                            (version == 0 || bookDefinitions[i].getVersion() == version)
                            ) {
                        rslt[rsltIndex++] = bookDefinitions[i];
                    }
                }
                if(rsltIndex > 0) {
                    return rslt;
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        return null;
    }

    double calcFee(String [] barCodes){
        double receivable = 0.0;
        for(String barCode : barCodes) {
            for (BookInstance book : books) {
                if (book != null && book.getBarCode().equalsIgnoreCase(barCode)) {
                    for (BookDefinition bookDefinition : bookDefinitions) {
                        if (bookDefinition != null && bookDefinition.getDefinitionId() == book.getDefinitionId()) {
                            receivable += bookDefinition.getPrice();
                            break;
                        }
                    }
                    break;
                }
            }
        }

        return receivable;
    }

    double sellBook(String [] barCodes, double money, String employeeName) {
        double receivable = 0.0;
        for(String barCode : barCodes) {
            for (BookInstance book : books) {
                if (book != null && book.getBarCode().equalsIgnoreCase(barCode)) {
                    for (BookDefinition bookDefinition : bookDefinitions) {
                        if (bookDefinition != null && bookDefinition.getDefinitionId() == book.getDefinitionId()) {
                            bookDefinition.setBooksNum(bookDefinition.getBooksNum() - 1);
                            receivable += bookDefinition.getPrice();
                            break;
                        }
                    }
                    book = null;
                    break;
                }
            }
        }

        curBookNum -= barCodes.length;
        cashbox.makeMoney(money, money - receivable, employeeName);
        return money - receivable;
    }

    int getAvailInboundNum(){
        return MaxBookNum - curBookNum;
    }

    private boolean validateBookDefinition(String [] args){
        if(curBookDefNum == MaxBookDefNum) {
            System.out.println("图书种类已达到最大值：" + MaxBookDefNum);
            return false;
        }

        if(args.length != 9) {
            System.out.println("输入格式有误，正确的输入格式为：“书名 " +
                    "作者 出版社 版本 出版日期 ISBN 定价 书架号 书架排号”。");
            return false;
        }

        if(!args[3].matches("^[1-9]\\d?$")) {
            System.out.println("书籍版本号应该最多为2位正整数。");
            return false;
        }

        if(!args[4].matches("^\\d{4}-((0[1-9])|(1[0-2]))-(([0-2][1-9])|3[0,1])$")) {
            System.out.println("请输入正确的日期格式“YYYY-MM-DD”。");
            return false;
        }

        if(!args[6].matches("^\\d+(\\.\\d+)?$")) {
            System.out.println("请输入正确的书籍价格，必须为非负整数。");
            return false;
        }

        if(!(args[7].matches("^[1-9]\\d*$") && args[8].matches("^[1-9]\\d*$"))) {
            System.out.println("请输入正确的书架编号和书架排号，必须为正整数。");
            return false;
        }

        return true;
    }

    private boolean validateBookSearch(String [] args){
        if(curBookDefNum == 0){
            System.out.println("目前书店无任何书籍。");
        }

        if(args[0].isEmpty()){
            System.out.println("书名不可为空。");
            return false;
        }

        if(args.length > 3 && !args[3].matches("^[1-9]\\d*$")){
            System.out.println("书籍版本号必须为正整数。");
            return false;
        }

        return true;
    }

    private BookDefinition [] bookDefinitions;
    private BookInstance [] books;
    private int curBookNum;
    private int curBookDefNum;
    private static final int MaxBookNum = 5000;
    private static final int MaxBookDefNum = 100;
    private Cashbox cashbox = new Cashbox();
}
