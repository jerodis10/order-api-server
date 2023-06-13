package com.jerodis.kr.co._29cm.homework.domain;

import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.isNumeric;

@Getter
@Builder
public class Item {

	@NonNull
	private final Long itemNo;

	@NonNull
	private final String itemName;

	@NonNull
	private final Long price;

	@NonNull
	@Min(1)
	private Long quantity;

	@NonNull
	@Min(1)
	private Long stock;


	public void decreaseStock(Long quantity) {
		this.stock -= quantity;
		if (this.stock < 0) {
			throw new SoldOutException("SoldOutException 발생. 상품량이 재고량보다 큽니다.");
		}
	}

	public void increaseQuantity(Long quantity) {
		this.quantity += quantity;
	}

	public static List<Item> toListItem(List<String[]> paramList) {
		List<Item> items = new ArrayList<>();

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

			Item item = Item.builder()
					.itemNo(Long.valueOf(line[0]))
					.itemName(itemName)
					.price(Long.valueOf(line[index]))
					.quantity(Long.valueOf(line[index + 1]))
					.stock(Long.valueOf(line[index + 1]))
					.build();

			items.add(item);
		}

		return items;
	}

	public static Item toItem(String[] line) {
		String itemName = "";
		int index = 0;

		for (int i = 1; i < line.length; i++) {
			if (isNumeric(line[i])) {
				index = i;
				break;
			} else {
				itemName += line[i];
			}
		}

		return Item.builder()
				.itemNo(Long.valueOf(line[0]))
				.itemName(itemName)
				.price(Long.valueOf(line[index]))
				.quantity(Long.valueOf(line[index + 1]))
				.stock(Long.valueOf(line[index + 1]))
				.build();
	}

	@Override
	public String toString() {
		return String.format("%s - %d개", itemName, quantity);
	}



//	public Item(Long itemNo, String itemName, Long price, Long quantity) {
//		this.itemNo = itemNo;
//		this.itemName = itemName;
//		this.price = price;
//		this.quantity = quantity;
//	}

//	public static Map<String, Item> toMapItem(Map<String, String[]> paramMap) {
//		Map<String, Item> itemMap = new HashMap<>();
//
//		for (String key : paramMap.keySet()) {
//			if(!isNumeric(key)) continue;
//
//			String itemName = "";
//			int index = 0;
//
//			for (int i = 1; i < paramMap.get(key).length; i++) {
//				if (isNumeric(paramMap.get(key)[i])) {
//					index = i;
//					break;
//				} else {
//					itemName += paramMap.get(key)[i];
//				}
//			}
//
//			Item item = Item.builder()
//					.itemNo(Long.valueOf(paramMap.get(key)[0]))
//					.itemName(itemName)
//					.price(Long.valueOf(paramMap.get(key)[index].toString()))
//					.quantity(Long.valueOf(paramMap.get(key)[index + 1].toString()))
//					.build();
//
//			itemMap.put(paramMap.get(key)[0], item);
//		}
//
//		return itemMap;
//	}

}
