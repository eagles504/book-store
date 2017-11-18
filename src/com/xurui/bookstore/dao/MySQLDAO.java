package com.xurui.bookstore.dao;

import com.xurui.bookstore.Constants.Constants;
import com.xurui.bookstore.exception.BookStoreException;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MySQLDAO {
    public static MySQLDAO getInstance() {
        if (instance == null) {
            instance = new MySQLDAO();
        }

        return instance;
    }

    public Connection getConnection() throws BookStoreException {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BookStoreException(Constants.ExceptionMsg.OTHER_ERROR.getName(),
                    Constants.ExceptionMsg.OTHER_ERROR.getValue());
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_store?" +
                    "useSSL=false&serverTimezone=CST&user=book_store_admin&password=123456");
        } catch (SQLException e) {
            throw new BookStoreException(Constants.ExceptionMsg.DB_ERROR.getName(),
                    String.format(Constants.ExceptionMsg.DB_ERROR.getValue(),
                            e.getMessage(), e.getSQLState(), e.getErrorCode()));
        }

        return conn;
    }

    public void closeAll() throws BookStoreException {
        for (Connection conn : conns) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new BookStoreException(Constants.ExceptionMsg.DB_ERROR.getName(),
                        String.format(Constants.ExceptionMsg.DB_ERROR.getValue(),
                                e.getMessage(), e.getSQLState(), e.getErrorCode()));
            }
        }

        conns.clear();
    }

    /* book definition accessor */
    public void updateBookDef(@NotNull Connection conn, @NotNull BookDefinitionEntity def,
                                 @NotNull Constants.Operation oper) throws BookStoreException {
        String sqlStr = null;
        switch (oper) {
            case ADD:
                sqlStr = "INSERT INTO book_def(name,author,publisher,version,ISBN," +
                        "publish_date,price,shelf_no,shelf_row) " +
                        "VALUES(\"%s\",\"%s\",\"%s\",%d,\"%s\",\"%s\",%f,%d,%d)";
                sqlStr = String.format(sqlStr, def.getName(), def.getAuthor(), def.getPublisher(), def.getVersion(),
                        def.getISBN(), (new SimpleDateFormat("yyyy-MM-dd")).format(def.getPublishDate()),
                        def.getPrice(), def.getShelfNo(), def.getShelfRow());
                break;
            case MOD:
                sqlStr = "UPDATE book_def SET name=\"%s\",author=\"%s\",publisher=\"%s\",version=%d,ISBN=\"%s\"," +
                        "publish_date=\"%s\",price=%f,book_count=%d,shelf_no=%d,shelf_row=%d WHERE id=%d";
                sqlStr = String.format(sqlStr, def.getName(), def.getAuthor(), def.getPublisher(), def.getVersion(), def.getISBN(),
                        (new SimpleDateFormat("yyyy-MM-dd").format(def.getPublishDate())), def.getPrice(),
                        def.getBookCount(), def.getShelfNo(), def.getShelfRow(), def.getId());
                break;
            case DEL:
                sqlStr = "DELETE FROM book_def where id=" + def.getId();
                break;
        }

        updateData(conn, sqlStr);
    }

    public List<BookDefinitionEntity> qryBookDef(@NotNull Connection conn, int id, String name, String author)
            throws BookStoreException {
        String sqlStr = "SELECT * FROM book_def WHERE ";
        if (id == 0 && name == null && author == null) {
            throw new BookStoreException(Constants.ExceptionMsg.LACK_OF_CONDITION.getName(),
                    Constants.ExceptionMsg.LACK_OF_CONDITION.getValue());
        } else if (id == 0) {
            sqlStr = sqlStr.concat((name != null ? "name=" + '\"' + name + '\"' : "") +
                    (name != null && author != null ? " AND " : "") +
                    (author != null ? "author=" + '\"' + author + '\"' : ""));
        } else {
            sqlStr = sqlStr.concat("id=" + id);
        }

        return qryData(conn, sqlStr, TableName.book_def);
    }

    /* book instance accessor */
    public void updateBookInst(@NotNull Connection conn, @NotNull BookInstanceEntity inst,
                                  @NotNull Constants.Operation oper)  throws BookStoreException {
        String sqlStr = null;
        switch (oper) {
            case ADD:
                sqlStr = "INSERT INTO book_inst(book_id, bar_code,book_store_id,status) VALUES(%d, \"%s\",%d,%d)";
                sqlStr = String.format(sqlStr, inst.getBookId(), inst.getBarCode(), inst.getBookStoreId(), inst.getStatus());
                break;
            case MOD:
                sqlStr = "UPDATE book_inst SET book_store_id=%d,status=%d WHERE bar_code=\"%s\"";
                sqlStr = String.format(sqlStr, inst.getBookStoreId(), inst.getStatus(), inst.getBarCode());
                break;
            case DEL:
                sqlStr = "DELETE FROM book_inst WHERE bar_code=" + inst.getBarCode();
                break;
        }

        updateData(conn, sqlStr);
    }

    public BookInstanceEntity qryBookInst(@NotNull Connection conn, @NotNull String barCode)
            throws BookStoreException {
        String sqlStr = "SELECT * FROM book_inst WHERE bar_code=" + '\"' + barCode + '\"';
        List<BookInstanceEntity> result = qryData(conn, sqlStr, TableName.book_inst);
        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }

    /* book store accessor */
    public void updateBookStore(@NotNull Connection conn, @NotNull BookStoreEntity store,
                                   @NotNull Constants.Operation oper)  throws BookStoreException {
        String sqlStr = null;
        switch (oper) {
            case ADD:
                sqlStr = "INSERT INTO book_store(name,addr,online_flag,status) VALUES(\"%s\",\"%s\",%d,%d)";
                sqlStr = String.format(sqlStr, store.getName(), store.getAddr(),
                        store.getOnlineFlag() ? 1 : 0, store.getStatus());
                break;
            case MOD:
                sqlStr = "UPDATE book_store SET name=\"%s\",addr=\"%s\"," +
                        "online_flag=%d,status=%d WHERE id=%d";
                sqlStr = String.format(sqlStr, store.getName(), store.getAddr(), store.getOnlineFlag() ? 1: 0,
                        store.getStatus(), store.getId());
                break;
            case DEL:
                sqlStr = "DELETE FROM book_store WHERE id=" + store.getId();
                break;
        }

        updateData(conn, sqlStr);
    }

    public List<BookStoreEntity> qryBookStore(@NotNull Connection conn, int id, String name, String addr)
            throws BookStoreException {
        if (id == 0 && name == null && addr == null) {
            throw new BookStoreException(Constants.ExceptionMsg.LACK_OF_CONDITION.getName(),
                    Constants.ExceptionMsg.LACK_OF_CONDITION.getValue());
        }


        String sqlStr = "SELECT * FROM book_store WHERE ";
        if(id == 0) {
            sqlStr = sqlStr.concat((name != null ? "name=" + '\"' + name + '\"': "") +
                    (name != null && addr != null ? " AND " : "") +
                    (addr != null ? "addr=" + '\"' + addr + '\"': ""));
        }else {
            sqlStr = sqlStr.concat("id=" + id);
        }

        return qryData(conn, sqlStr, TableName.book_store);
    }

    /* cash box accessor */
    public void updateCashBox(@NotNull Connection conn, @NotNull CashBoxEntity box,
                                 @NotNull Constants.Operation oper)  throws BookStoreException {
        String sqlStr = null;
        switch (oper) {
            case ADD:
                sqlStr = "INSERT INTO cash_box(book_store_id,balance,status) VALUES(%d,%f,%d)";
                sqlStr = String.format(sqlStr, box.getBookStoreId(), box.getBalance(), box.getStatus());
                break;
            case MOD:
                sqlStr = "UPDATE cash_box SET balance=%f,status=%d WHERE id=%d";
                sqlStr = String.format(sqlStr, box.getBalance(), box.getStatus(), box.getId());
                break;
            case DEL:
                sqlStr = "DELETE FROM cash_box WHERE id=" + box.getId();
                break;
        }

        updateData(conn, sqlStr);
    }

    public List<CashBoxEntity> qryCashBox(@NotNull Connection conn, int id, int storeId)  throws BookStoreException {
        if(id == 0 && storeId == 0) {
            throw new BookStoreException(Constants.ExceptionMsg.LACK_OF_CONDITION.getName(),
                    Constants.ExceptionMsg.LACK_OF_CONDITION.getValue());
        }

        String sqlStr = "SELECT * FROM cash_box WHERE ";
        if(id != 0) {
            sqlStr = sqlStr.concat("id=" + id);
        }else {
            sqlStr = sqlStr.concat("book_store_id=" + storeId);
        }

        return qryData(conn, sqlStr, TableName.cash_box);
    }

    /* payment log accessor */
    public void updatePaymentLog(@NotNull Connection conn, @NotNull PaymentLogEntity log,
                                    @NotNull Constants.Operation oper)  throws BookStoreException {
        String sqlStr = null;
        switch (oper) {
            case ADD:
                sqlStr = "INSERT INTO payment_log(cash_box_id,income,expense,employee,customer,date) " +
                        "VALUES(%d,%f,%f,\"%s\",\"%s\",\"%s\")";
                sqlStr = String.format(sqlStr, log.getCashBoxId(), log.getIncome(), log.getExpense(), log.getEmployee(),
                        log.getCustomer(), (new SimpleDateFormat("yyyy-MM-dd")).format(log.getDate()));
                break;
            case MOD:
            case DEL:
                return;
        }

        updateData(conn, sqlStr);
    }

    public List<PaymentLogEntity> qryPaymentLog(@NotNull Connection conn, int id, int cashBoxId,
                                                String employee, String customer, Date date)  throws BookStoreException {
        if (id == 0 && cashBoxId == 0 && employee == null && customer == null && date == null) {
            throw new BookStoreException(Constants.ExceptionMsg.LACK_OF_CONDITION.getName(),
                    Constants.ExceptionMsg.LACK_OF_CONDITION.getValue());
        }

        String sqlStr = "SELECT * FROM payment_log WHERE ";
        if(id == 0) {
            sqlStr = sqlStr.concat((cashBoxId != 0 ? "cash_box_id=" + cashBoxId : "") +
                    (cashBoxId != 0 && (employee != null || customer != null || date != null) ? " AND " : "") +
                    (employee != null ? "employee=" + '\"' + employee + '\"': "") +
                    (employee != null && (customer != null || date != null) ? " AND " : "") +
                    (customer != null ? "customer=" + '\"' + customer + '\"': "") +
                    (customer != null && date != null ? " AND " : "") +
                    (date != null ? "`date`=STR_TO_DATE(\"" +
                            (new SimpleDateFormat("yyyy-MM-dd")).format(date) + "\", \"%Y-%m-%d\")" : ""));
        }else {
            sqlStr = sqlStr.concat("id=" + id);
        }

        return qryData(conn, sqlStr, TableName.payment_log);
    }

    private enum TableName {
        book_def,
        book_store,
        book_inst,
        cash_box,
        payment_log
    }

    private void updateData(@NotNull Connection conn, @NotNull String sqlStr) throws BookStoreException {
        Statement stmt = null;
        System.out.println(sqlStr);
        try {
            stmt = conn.createStatement();
            stmt.execute(sqlStr);
        } catch (SQLException e) {
            throw new BookStoreException(Constants.ExceptionMsg.DB_ERROR.getName(),
                    String.format(Constants.ExceptionMsg.DB_ERROR.getValue(),
                            e.getMessage(), e.getSQLState(), e.getErrorCode()));
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new BookStoreException(Constants.ExceptionMsg.DB_ERROR.getName(),
                            String.format(Constants.ExceptionMsg.DB_ERROR.getValue(),
                                    e.getMessage(), e.getSQLState(), e.getErrorCode()));
                }
            }
        }
    }

    private List qryData(@NotNull Connection conn, @NotNull String sqlStr, @NotNull TableName tableName)
            throws BookStoreException{
        List result = new LinkedList<>();
        Statement stmt = null;
        ResultSet rs = null;
        System.out.println(sqlStr);

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStr);
            switch (tableName) {
                case book_def:
                    while (rs.next()) {
                        result.add(new BookDefinitionEntity(rs.getInt(1), rs.getString(2),
                                rs.getString(3), rs.getString(4), rs.getInt(5),
                                rs.getDate(6), rs.getString(7), rs.getDouble(8),
                                rs.getInt(9), rs.getInt(10), rs.getInt(11)));
                    }
                    break;
                case book_store:
                    while (rs.next()) {
                        result.add(new BookStoreEntity(rs.getInt(1), rs.getString(2),
                                rs.getString(3), rs.getInt(4) == 1,
                                rs.getInt(5)));
                    }
                    break;
                case book_inst:
                    while (rs.next()) {
                        result.add(new BookInstanceEntity(rs.getInt(1), rs.getString(2),
                                rs.getInt(3), rs.getInt(4)));
                    }
                    break;
                case cash_box:
                    while (rs.next()) {
                        result.add(new CashBoxEntity(rs.getInt(1), rs.getInt(2),
                                rs.getDouble(3), rs.getInt(4)));
                    }
                    break;
                case payment_log:
                    while (rs.next()) {
                        result.add(new PaymentLogEntity(rs.getInt(1), rs.getInt(2),
                                rs.getDouble(3), rs.getDouble(4), rs.getString(5),
                                rs.getString(6), rs.getDate(7)));
                    }
                    break;
            }
        } catch (SQLException e) {
            throw new BookStoreException(Constants.ExceptionMsg.DB_ERROR.getName(),
                    String.format(Constants.ExceptionMsg.DB_ERROR.getValue(),
                            e.getMessage(), e.getSQLState(), e.getErrorCode()));
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    private MySQLDAO() {
        this.conns = new LinkedList<>();
    }

    private List<Connection> conns;
    private static MySQLDAO instance = null;
}
