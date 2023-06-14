package com.jerodis.kr.co._29cm.homework.domain;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class OrderDetail {
    private final Item item;

    @NonNull
    private Long orderQuantity;

    public OrderDetail(Stock stock, @NonNull Long orderQuantity) {
        this.item = Item.builder()
                .itemNo(stock.getItemNo())
                .itemName(stock.getItemName())
                .price(stock.getPrice())
                .build();

        this.orderQuantity = orderQuantity;
    }

    public Long getAmount() {
        return item.getPrice() * this.orderQuantity;
    }

    public void increaseQuantity(Long orderQuantity) {
        this.orderQuantity += orderQuantity;
    }

    @Override
    public String toString() {
        return String.format("%s - %d개", this.item.getItemName(), orderQuantity);
    }

}
