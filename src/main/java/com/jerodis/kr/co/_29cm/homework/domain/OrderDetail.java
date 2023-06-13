package com.jerodis.kr.co._29cm.homework.domain;

import lombok.Getter;

@Getter
public final class OrderDetail {
    private final Item item;

//    private final Long purchaseQuantity;

    public OrderDetail(Item item) {
        this.item = item;
//        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getAmount() {
        return item.getPrice() * item.getQuantity();
    }

}
