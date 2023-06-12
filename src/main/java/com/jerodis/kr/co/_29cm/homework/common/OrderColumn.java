package com.jerodis.kr.co._29cm.homework.common;

public enum OrderColumn {
    ITEM_NO("상품번호"),
    ITEM_NAME("상품명"),
    AMOUNT("판매가격"),
    QUANTITY("재고수량")
    ;

    public String columnName;

    OrderColumn(String columnName) {
        this.columnName = columnName;
    }
}
