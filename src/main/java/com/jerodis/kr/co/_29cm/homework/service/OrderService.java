package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.OrderDetail;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final Printer printer;
	private final InputReader inputReader;

	public List<Item> getItems() {
		return orderRepository.findAllItem();
	}

	public Order requestOrder() {
		Map<String, OrderDetail> orderMap = new HashMap<>();
//		Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

		while (true) {
			printer.print("상품번호: ");
			String inputItemNo = inputReader.read();
			if(!OrderCommandValidator.validateItemNo(inputItemNo)) break;
			Item savedItem = orderRepository.findOneItem(inputItemNo)
					.orElseThrow(() -> new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, inputItemNo));

			printer.print("수량: ");
			String inputQuantity = inputReader.read();
			if(!OrderCommandValidator.validateQuantity(inputQuantity)) break;
			Long itemQuantity = Long.valueOf(inputQuantity);

			Item item = Item.builder()
					.itemNo(savedItem.getItemNo())
					.itemName(savedItem.getItemName())
					.price(savedItem.getPrice())
					.quantity(itemQuantity)
					.stock(savedItem.getStock())
					.build();

			orderProcess(item, inputItemNo, itemQuantity, orderMap);
		}

		return new Order(new ArrayList<>(orderMap.values()));
	}

	public synchronized void orderProcess(Item item, String inputItemNo, Long itemQuantity, Map<String, OrderDetail> orderMap) {
		if (orderMap.containsKey(inputItemNo)) {
//			Long originalItemQuantity = orderMap.get(inputItemNo).getItem().getQuantity();
			item.decreaseStock(itemQuantity);
//			item.decreaseStock(originalItemQuantity + itemQuantity);
			item.increaseQuantity(itemQuantity);
//			item.increaseQuantity(originalItemQuantity);
			orderMap.put(inputItemNo, new OrderDetail(item));
		} else {
			item.decreaseStock(itemQuantity);
			orderMap.put(inputItemNo, new OrderDetail(item));
		}
	}

}
