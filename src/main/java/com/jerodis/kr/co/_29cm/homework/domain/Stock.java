package com.jerodis.kr.co._29cm.homework.domain;

import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.isNumeric;

@Getter
public class Stock {
    @NonNull
    private final Long itemNo;

    private final String itemName;

    @NonNull
    private final Long price;

    @NonNull
    private Long stockQuantity;

    @Builder
    public Stock(Long itemNo, String itemName, Long price, Long stockQuantity) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void decreaseStock(Long quantity) {
		this.stockQuantity -= quantity;
		if (this.stockQuantity < 0L) {
			throw new SoldOutException("SoldOutException 발생. 상품량이 재고량보다 큽니다.");
		}
	}

    public static List<Stock> toListStock(List<String[]> paramList) {
        List<Stock> stocks = new ArrayList<>();

        for (String[] line : paramList) {
            int index = 0;
            String itemName = "";
            for (int i = 1; i < line.length; i++) {
                if (isNumeric(line[i])) {
                    index = i;
                    break;
                } else {
                    itemName += line[i];
                }
            }

            Stock stock = Stock.builder()
                    .itemNo(Long.valueOf(line[0]))
                    .itemName(itemName)
                    .price(Long.valueOf(line[index]))
                    .stockQuantity(Long.valueOf(line[index + 1]))
                    .build();

            stocks.add(stock);
        }

        return stocks;
    }

}
