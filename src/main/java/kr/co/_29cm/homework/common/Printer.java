package kr.co._29cm.homework.common;

import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderDetail;
import kr.co._29cm.homework.domain.Stock;

import java.util.List;

import static kr.co._29cm.homework.common.NumberUtil.numberFormatter;

public interface Printer {
    static final String LINE_SEPARATOR = "-----------------------------------";
    static final String LONG_LINE_SEPARATOR = "-------------------------------------------------------------------------------------------------------";

    void print(String s);

    void println(String s);

    void print(List<Stock> stocks);

    void print(Order order);

    void initInput();

    static String orderDetailPrint(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("주문 내역: ").append("\n");
        sb.append(LINE_SEPARATOR).append("\n");
        for (OrderDetail orderDetail: order.getOrderDetails()) {
            sb.append(orderDetail.toString()).append("\n");
        }

        sb.append(LINE_SEPARATOR).append("\n");
        sb.append("주문금액: ").append(numberFormatter(order.getOrderAmount())).append("원").append("\n");
        if (order.getDeliveryFee() > 0) {
            sb.append("배송비: ").append(numberFormatter(order.getDeliveryFee())).append("원").append("\n");
        }

        sb.append(LINE_SEPARATOR).append("\n");
        sb.append("지불금액: ").append(numberFormatter(order.getPayment())).append("원").append("\n");
        sb.append(LINE_SEPARATOR).append("\n");

        return sb.toString();
    }
}
