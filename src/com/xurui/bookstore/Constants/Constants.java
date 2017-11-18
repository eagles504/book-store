package com.xurui.bookstore.Constants;

public class Constants {
    public static enum ExceptionMsg {
        DB_ERROR("1001", "DB Exception:%s, DB SQL State:%s, Vendor Error Code:%d."),
        DATA_NOT_EXIST("1002", "The data to be updated/deleted does not exist."),
        BOOK_DEF_NOT_EXIST("1003", "The book definition does not exist."),
        LACK_OF_CONDITION("1004", "At least one query condition should be set."),
        BOOK_DEF_ATTR_INVALID("1005", "The attribute value of book definition is invalid."),
        BAR_CODE_INVALID("1006", "Bar code is invalid."),
        NO_EMPLOYEE_INPUTTED("1007", "Employee name should be provided."),
        NO_CUSTOMER_INPUTTED("1008", "Customer name should be provided."),
        INCOME_MONEY_INVALID("1009", "Income money is invalid or less than dues."),
        BOOK_INST_NOT_EXIST("1010", "The book(bar code) %s does not belong to the store."),
        OPER_NULL("1011", "The operation should be provided."),
        BOOK_STORE_NOT_EXIST("1012", "The book store does not exist."),
        BOOK_INST_SOLD("1013", "The book(bar code) %s has been sold."),
        OTHER_ERROR("9999", "Other error. Please contact the engineer.")
        ;

        private ExceptionMsg(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private String name;
        private String value;
    }

    public static enum Operation {
        ADD, MOD, DEL
    }
}
