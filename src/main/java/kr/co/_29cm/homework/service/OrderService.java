package kr.co._29cm.homework.service;

import kr.co._29cm.homework.common.InputReader;
import kr.co._29cm.homework.common.Printer;
import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderDetail;
import kr.co._29cm.homework.domain.Stock;
import kr.co._29cm.homework.exception.InvalidCommandException;
import kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import kr.co._29cm.homework.exception.NoRequestOrderException;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

			printer.print("수량: ");
			String inputQuantity = inputReader.read();
			if(!OrderCommandValidator.validateQuantity(inputQuantity)) break;
			Long orderQuantity = Long.valueOf(inputQuantity);

			orderProcess(findStock, orderQuantity, orderMap);
		}

		List<OrderDetail> orderDetails = new ArrayList<>(orderMap.values());
		if(!orderDetails.isEmpty()) return new Order(orderDetails);
		else throw new NoRequestOrderException("주문한 상품이 존재하지 않습니다.");
	}

	@Synchronized
	private void orderProcess(Stock stock, Long orderQuantity, Map<Long, OrderDetail> orderMap) {
		if (orderMap.containsKey(stock.getItemNo())) {
			if(validateStock(stock, orderQuantity)) stock.decreaseStock(orderQuantity);
			Long originalOrderQuantity = orderMap.get(stock.getItemNo()).getOrderQuantity();
			orderMap.put(stock.getItemNo(), new OrderDetail(stock, originalOrderQuantity + orderQuantity));
		} else {
			if(validateStock(stock, orderQuantity)) stock.decreaseStock(orderQuantity);
			orderMap.put(stock.getItemNo(), new OrderDetail(stock, orderQuantity));
		}
	}

	private boolean validateStock(Stock stock, Long orderQuantity) {
		if(stock.getStockQuantity() < orderQuantity) throw new SoldOutException("SoldOutException 발생. 상품량이 재고량보다 큽니다.");
		else return true;
	}

}