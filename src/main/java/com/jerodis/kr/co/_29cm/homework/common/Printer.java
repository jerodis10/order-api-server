package com.jerodis.kr.co._29cm.homework.common;

import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.OrderDetail;

import java.util.List;

public interface Printer {
    static final String LINE_SEPARATOR = "-----------------------------------";

    void print(String s);

    void println(String s);

    void print(List<Item> itemList);

    void print(Order order);

    void initInput();

    static String orderString(Order order) {
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

        return sb.toString();
    }
}
