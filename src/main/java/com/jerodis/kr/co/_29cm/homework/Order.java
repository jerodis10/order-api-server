package com.jerodis.kr.co._29cm.homework;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class Order {

	@NonNull
	String itemNo;

	@NonNull
	String itemName;

	@NonNull
	Long amount;

	@NonNull
	@Min(1)
	Long quantity;

	public static Map<String, Order> toOrder(Map<String, String[]> itemMap) {
		Map<String, Order> orderMap = new HashMap<>();

		for (String key : itemMap.keySet()) {
			if(!isNumeric(key)) continue;

			String itemName = "";
			int index = 0;

			for (int i = 1; i < itemMap.get(key).length; i++) {
				if (isNumeric(itemMap.get(key)[i])) {
					index = i;
					break;
				} else {
					itemName += itemMap.get(key)[i];
				}
			}

			Order order = Order.builder()
					.itemNo(itemMap.get(key)[0])
					.itemName(itemName)
					.amount(Long.valueOf(itemMap.get(key)[index].toString()))
					.quantity(Long.valueOf(itemMap.get(key)[index + 1].toString()))
					.build();

			orderMap.put(itemMap.get(key)[0], order);
		}

		return orderMap;
	}

	private static boolean isNumeric(String s)
	{
		try {
			Long.parseLong(s);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
