package com.jerodis.kr.co._29cm.homework.domain;

import lombok.Getter;

import java.util.List;

//@Builder
@Getter
public class Order {
    private static final Long MINIMUM_SHIPPING_FEE = 50_000L;
    private static final Long BASIC_SHIPPING_FEE = 0L;
    private static final Long SHIPPING_FEE = 2_000L;

    // 주문금액 숫자 포맷 적용
    private final Long orderAmount;

    // 주문금액 숫자 포맷 적용
    private final Long payment;

    private final List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return List.copyOf(orderDetails);
    }

    public Order(List<OrderDetail> orderDetails) {
        this.orderDetails = List.copyOf(orderDetails);
        this.orderAmount = getTotalAmount();
        this.payment = getTotalAmount();
    }

    public Long getTotalAmount() {
        return orderDetails.stream()
                .mapToLong(OrderDetail::getAmount)
                .sum();
    }

    public Long getDeliveryFee() {
        return getTotalAmount() > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;
    }

}
