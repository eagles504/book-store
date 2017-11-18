/*
package com.xurui;

import BookDefinitionEntity;
import BookInstanceEntity;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // write your code here
        redirectSysErr();

        restore();
        if(bookStore == null) {
            bookStore = new BookStore();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input;
            do {
                entryTips();
                input = reader.readLine();
                switch (input) {
                    case "1":
                        defineBooks();
                        break;
                    case "2":
                        inbound();
                        break;
                    case "3":
                        searchBooks(true);
                        break;
                    case "4":
                        sellBook();
                        break;
                }
            } while (!input.trim().equals("0"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void entryTips() {
        System.out.println("=======================主菜单=======================");
        System.out.println("请选择：");
        System.out.println(" 1：配置书籍种类");
        System.out.println(" 2：书籍入库");
        System.out.println(" 3：查找书籍");
        System.out.println(" 4：售出书籍");
        System.out.println(" 0：退出");
    }

    private static BookDefinitionEntity searchBooks(boolean prtTitle) {
        if (prtTitle) {
            System.out.println("=======================查找书籍=======================");
        }
        List<BookDefinitionEntity> searchResults;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputStr;
        try {
            do {
                System.out.println("请输入查询条件：“书名 作者 出版社 版本”");
                inputStr = reader.readLine();
                searchResults = bookStore.searchBooks(inputStr.trim().split("\\s+"));
                if (searchResults == null || searchResults.isEmpty()) {
                    System.out.println("未查询到符合条件的书籍，是否继续查找？（Y/N）");
                } else {
                    break;
                }
            } while (reader.readLine().trim().equalsIgnoreCase("Y"));
            if (searchResults != null && !searchResults.isEmpty()) {
                for (int i = 0; i < searchResults.size(); i++) {
                    searchResults.get(i).displayInfo();
                    System.out.println("是您要查找的书籍（Y/N)？");
                    if (reader.readLine().trim().equals("Y")) {
                        return searchResults.get(i);
                    } else if (i == searchResults.size() - 1) {
                        System.out.println("已到达最后一条结果，是否重新查看查询结果？（Y/N)");
                        if (reader.readLine().trim().equals("Y")) {
                            i = -1;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void defineBooks() {
        System.out.println("=======================配置书籍种类=======================");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputStr;
        try {
            do {
                System.out.println("请按照格式" +
                        "“书名 作者 出版社 版本 出版日期 ISBN 定价 书架号 书架排号”输入书籍种类定义：");
                inputStr = reader.readLine();
                if (bookStore.defineBook(inputStr.trim().split("\\s+"))) {
                    System.out.println("配置书籍种类成功。");
                }
                System.out.println("继续配置书籍种类？（Y/N）");
            } while (reader.readLine().equals("Y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void inbound() {
        System.out.println("=======================书籍入库=======================");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputStr;
        BookDefinitionEntity bd;
        try {
            List<BookInstanceEntity> books = new LinkedList<>();
            do {
                bd = searchBooks(false);
                if (bd != null) {
                    do {
                        System.out.println("请输入书籍的二维码：");
                        if (!(inputStr = reader.readLine()).trim().isEmpty()) {
                            books.add(new BookInstanceEntity(bd.getDefinitionId(), inputStr));
                        } else {
                            System.out.println("请输入正确的书籍二维码。");
                        }

                        System.out.println("二维码录入完毕？（Y/N)");
                    } while (reader.readLine().trim().equals("N"));

                    if(!books.isEmpty()) {
                        bookStore.inbound(books, bd);
                    }
                }

                System.out.println("继续录入其他种类书籍？（Y/N）");
            } while (reader.readLine().trim().equals("Y"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sellBook() {
        System.out.println("=======================售出书籍=======================");
        String[] barCodes;
        String inputStr;
        double actualIncome;
        double receivable;
        double change;
        String employeeName;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            do {
                barCodes = null;
                actualIncome = 0.0;
                employeeName = null;
                do {
                    System.out.println("请录入售出书籍的二维码，以空格隔开：");
                    inputStr = br.readLine();
                    if (inputStr.trim().isEmpty()) {
                        System.out.println("无效的二维码，是否重新录入？（Y/N）");
                    } else {
                        barCodes = inputStr.trim().split("\\s+");
                        break;
                    }
                } while (br.readLine().trim().equalsIgnoreCase("Y"));

                if (barCodes != null) {
                    receivable = bookStore.calcFee(barCodes);
                    System.out.println("应收金额：" + receivable);
                    do {
                        System.out.println("请输入实收金额：");
                        inputStr = br.readLine();
                        if (inputStr.trim().isEmpty() || !inputStr.matches("^[1-9]\\d*(\\.\\d+)?$")
                                || Double.parseDouble(inputStr.trim()) < receivable) {
                            System.out.println("实收金额录入错误（必须为正数，且不能少于应收金额），是否继续输入？（Y/N）");
                        } else {
                            actualIncome = Double.parseDouble(inputStr.trim());
                            break;
                        }
                    } while (br.readLine().trim().equalsIgnoreCase("Y"));
                }

                if (actualIncome > 0) {
                    do {
                        System.out.println("请输入收银员姓名：");
                        if ((inputStr = br.readLine()).trim().isEmpty()) {
                            System.out.println("姓名不可为空，是否继续输入？（Y/N）");
                        } else {
                            employeeName = inputStr.trim();
                            break;
                        }
                    } while (br.readLine().trim().equalsIgnoreCase("Y"));

                    if (employeeName != null) {
                        change = bookStore.sellBook(barCodes, actualIncome, employeeName);
                        System.out.println("书籍售出成功，应找零：" + change);
                    }
                }

                System.out.println("是否继续售出书籍？（Y/N）");
            } while (br.readLine().trim().equalsIgnoreCase("Y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void save() {
        try {
            FileOutputStream fos = new FileOutputStream("out" + File.separator  + "save" + File.separator +"bookStore.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bookStore);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void restore() {
        try {
            FileInputStream fis = new FileInputStream("out" + File.separator  + "save" + File.separator +"bookStore.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            bookStore = (BookStore) ois.readObject();
            ois.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void redirectSysErr() {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream("out" + File.separator  + "err" + File.separator + "err.log"));
            System.setErr(ps);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static BookStore bookStore;
}
*/
