package com.jerodis.kr.co._29cm.homework;

import java.util.List;

public class PrinterUtils {
    private static final String LINE_SEPARATOR = "-----------------------------------";
    private static final String PRINT_ITEM_FORMAT = "%-10s %-50s %-10s %s\n";

    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(List<Item> itemList) {
        System.out.printf(PRINT_ITEM_FORMAT,
                OrderColumn.ITEM_NO.columnName, OrderColumn.ITEM_NAME.columnName, OrderColumn.AMOUNT.columnName, OrderColumn.QUANTITY.columnName);

        System.out.println("------------------------------------------------------------------------------------------");

        for (Item item : itemList) {
            System.out.printf(PRINT_ITEM_FORMAT, item.getItemNo(), item.getItemName(), item.getPrice(), item.getQuantity());
        }
    }

    public static void print(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("주문 내역: ").append("\n");
        sb.append(LINE_SEPARATOR).append("\n");
        for (OrderDetail orderDetail: order.getOrderDetails()) {
            sb.append(orderDetail.getItem().toString()).append("\n");
        }

        sb.append(LINE_SEPARATOR).append("\n");
        sb.append("주문금액: ").append(order.getOrderAmount()).append("원").append("\n");
        if (order.getDeliveryFee() > 0) {
            sb.append("배송비: ").append(order.getDeliveryFee()).append("원").append("\n");
        }

        sb.append(LINE_SEPARATOR).append("\n");
        sb.append("지불금액: ").append(order.getDeliveryFee() + order.getPayment()).append("원").append("\n");
        sb.append(LINE_SEPARATOR).append("\n");

        println(sb.toString());
    }

}
