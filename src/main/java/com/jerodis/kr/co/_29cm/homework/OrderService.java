package com.jerodis.kr.co._29cm.homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ResourceUtils;

public class OrderService {

	public void printOrderAll() {
		try {
			File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "items.csv");
			StringBuffer stringBuffer = new StringBuffer();

			List<String[]> itemList = Files.lines(file.toPath())
				.map(line -> line.split(","))
				.toList();

			for (int i = 0; i < itemList.size(); i++) {
				for (int j = 0; j < itemList.get(i).length; j++) {
					stringBuffer.append(itemList.get(i)[j] + "     ");
				}
				stringBuffer.append("\r\n");
			}

			System.out.println(stringBuffer.toString());
		} catch (IOException e) {
		}
	}

	public String orderDetail(Map<String, String[]> itemsMap) {
		StringBuffer stringBuffer = new StringBuffer();
		Map<String, Order> newItemMap = new HashMap<>();

		for (String key : itemsMap.keySet()) {
			Order order = Order.builder()
				.itemNo(itemsMap.get(key)[0])
				.itemName(itemsMap.get(key)[1])
				.amount(Long.valueOf(itemsMap.get(key)[2]))
				.quantity(Long.valueOf(itemsMap.get(key)[3]))
				.build();

			newItemMap.put(itemsMap.get(key)[0], order);
		}


		return "";
	}
}
