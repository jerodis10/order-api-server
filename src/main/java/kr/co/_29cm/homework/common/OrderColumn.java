package kr.co._29cm.homework.common;

import lombok.Getter;

@Getter
public enum OrderColumn {
    ITEM_NO("상품번호"),
    ITEM_NAME("상품명"),
    AMOUNT("판매가격"),
    QUANTITY("재고수량")
    ;

    private final String columnName;

    OrderColumn(String columnName) {
        this.columnName = columnName;
    }
}
