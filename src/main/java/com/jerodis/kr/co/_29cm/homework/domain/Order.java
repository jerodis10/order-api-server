package com.jerodis.kr.co._29cm.homework.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    private static final Long MINIMUM_SHIPPING_AMOUNT = 50_000L;
    private static final Long BASIC_SHIPPING_AMOUNT = 0L;
    private static final Long SHIPPING_AMOUNT = 2_000L;

    private Long orderAmount;

    private Long payment;

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return List.copyOf(orderDetails);
    }

    public Order(List<OrderDetail> orderDetails) {
        this.orderDetails = List.copyOf(orderDetails);
        this.orderAmount = getTotalAmount();
        this.payment = getTotalAmount() + getDeliveryFee();
    }

    public Long getTotalAmount() {
        return orderDetails.stream()
                .mapToLong(OrderDetail::getAmount)
                .sum();
    }

    public Long getDeliveryFee() {
        return getTotalAmount() > MINIMUM_SHIPPING_AMOUNT ? BASIC_SHIPPING_AMOUNT : SHIPPING_AMOUNT;
    }

}
