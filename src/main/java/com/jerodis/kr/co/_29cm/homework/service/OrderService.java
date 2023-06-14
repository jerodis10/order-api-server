package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.OrderDetail;
import com.jerodis.kr.co._29cm.homework.domain.Stock;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Validated
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final Printer printer;
	private final InputReader inputReader;

	public List<Stock> getItems() {
		return orderRepository.findAllItem();
	}

	public Order requestOrder() {
		Map<Long, OrderDetail> orderMap = new HashMap<>();

		while (true) {
			printer.print("상품번호: ");
			String inputItemNo = inputReader.read();
			if(!OrderCommandValidator.validateItemNo(inputItemNo)) break;
			Stock findStock = orderRepository.findStockByItemNo(inputItemNo);
			if(findStock == null) throw new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, inputItemNo);
//					.orElseThrow(() -> new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, inputItemNo));

			printer.print("수량: ");
			String inputQuantity = inputReader.read();
			if(!OrderCommandValidator.validateQuantity(inputQuantity)) break;
			Long orderQuantity = Long.valueOf(inputQuantity);

			orderProcess(findStock, orderQuantity, orderMap);
		}

		return new Order(new ArrayList<>(orderMap.values()));
	}

	@Synchronized
	private void orderProcess(Stock stock, Long orderQuantity, Map<Long, OrderDetail> orderMap) {
		if (orderMap.containsKey(stock.getItemNo())) {
			stock.decreaseStock(orderQuantity);
			Long originalOrderQuantity = orderMap.get(stock.getItemNo()).getOrderQuantity();
			orderMap.put(stock.getItemNo(), new OrderDetail(stock, originalOrderQuantity + orderQuantity));
		} else {
			stock.decreaseStock(orderQuantity);
			orderMap.put(stock.getItemNo(), new OrderDetail(stock, orderQuantity));
		}
	}

}