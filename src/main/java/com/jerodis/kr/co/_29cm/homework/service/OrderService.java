package com.jerodis.kr.co._29cm.homework.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.OrderDetail;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.ItemNotFoundException;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final Printer printer;
	private final InputReader inputReader;
//	private final InputReader inputReader = new InputReader();

	public List<Item> getItems() {
		return orderRepository.findAllItem();
	}

	public Order requestOrder() throws IOException {
		Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();
//		Order order = null;

		while (true) {
//				PrinterUtils.print("상품번호: ");
			printer.print("상품번호: ");
			String itemNo = inputReader.read();

			if (!StringUtils.hasText(itemNo)) break;
			if(!Item.isNumeric(itemNo)) throw new InvalidCommandException(itemNo + " - 입력한 상품번호가 적절하지 않습니다.");
			Item savedItem = orderRepository.findOneItem(Long.valueOf(itemNo))
					.orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지 않습니다."));

//				PrinterUtils.print("수량: ");
			printer.print("수량: ");
			String InputQuantity = inputReader.read();
			if(!Item.isNumeric(InputQuantity)) throw new InvalidCommandException(itemNo + " - 입력한 상품번호가 적절하지 않습니다.");
			Long itemQuantity = Long.valueOf(InputQuantity);

			Item item = Item.builder()
					.itemNo(savedItem.getItemNo())
					.itemName(savedItem.getItemName())
					.price(savedItem.getPrice())
					.quantity(itemQuantity)
					.stock(savedItem.getQuantity())
					.build();

			if (orderMap.containsKey(itemNo)) {
				Long originalItemQuantity = orderMap.get(itemNo).getItem().getQuantity();
				item.minusStock(originalItemQuantity + itemQuantity);
				item.plusQuantity(originalItemQuantity);
				orderMap.put(itemNo, new OrderDetail(item));
//					orderMap.put(itemNo, new OrderDetail(item, originalItemQuantity + itemQuantity));
			} else {
				item.minusStock(itemQuantity);
				orderMap.put(itemNo, new OrderDetail(item));
//					orderMap.put(itemNo, new OrderDetail(item, itemQuantity));
			}
		}

//			order = new Order(new ArrayList<>(orderMap.values()));


		return new Order(new ArrayList<>(orderMap.values()));

//		return new ArrayList<>(orderMap.values());
	}



//	public boolean findAllOrder(String command) {
//		System.out.println("");
//
//		try {
//			if (command.equals("o") || command.equals("order")) {
//				StringBuffer stringBuffer = new StringBuffer();
//				File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + FILE_NAME);
//
//				List<String[]> itemList = Files.lines(file.toPath())
//						.map(line -> line.split(","))
//						.toList();
//
//				for (int i = 0; i < itemList.size(); i++) {
//					for (int j = 0; j < itemList.get(i).length; j++) {
//						stringBuffer.append(itemList.get(i)[j] + "     ");
//					}
//					stringBuffer.append("\r\n");
//				}
//
//				System.out.println(stringBuffer.toString());
//
//				return true;
//			} else if(command.equals("q") || command.equals("quit")) {
//				return false;
//			}
//		} catch (IOException e) {
//		}
//
//		return false;
//	}

//	public void findOrder() {
//		List<String> itemNoList = new ArrayList<>();
//		List<String> itemQuantityList = new ArrayList<>();
//		BufferedReader br;
//
//		try {
//			File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + FILE_NAME);
//			Map<String, String[]> itemMap = Files.lines(file.toPath())
//					.map(line -> line.split(","))
//					.collect(Collectors.toMap(p1 -> p1[0], p2 -> p2));
//
//			Map<String, Item> orderMap = Item.toMapOrder(itemMap);
//
//			while (true) {
//				System.out.print("상품번호: ");
//				br = new BufferedReader(new InputStreamReader(System.in));
//				String itemNo = br.readLine();
//				if (StringUtils.hasText(itemNo))
//					itemNoList.add(itemNo);
//
//				System.out.print("수량: ");
//				br = new BufferedReader(new InputStreamReader(System.in));
//				String itemQuantity = br.readLine();
//				if (StringUtils.hasText(itemQuantity))
//					itemQuantityList.add(itemQuantity);
//
//				if (!StringUtils.hasText(itemNo) && !StringUtils.hasText(itemQuantity)) {
//					if (itemNoList.size() > 0 && itemQuantityList.size() > 0) {
//						System.out.println("주문 내역: ");
//						System.out.println("----------------------------");
//
//						Long orderSum = 0L;
//						for (int i = 0; i < itemNoList.size(); i++) {
//							 String itemName = orderMap.get(itemNoList.get(i)).itemName;
//							 Long orderAmount = orderMap.get(itemNoList.get(i)).amount;
//							 Long orderQuantity = Long.parseLong(itemQuantityList.get(i));
//							 orderSum += (orderAmount * orderQuantity);
//
//							 System.out.printf("%s - %d개", itemName, orderQuantity);
//							 System.out.println("");
//						}
//
//						System.out.println("----------------------------");
//						System.out.printf("주문금액: %s원", orderSum); // 주문금액 숫자 포맷 적용
//						Long deliveryFee = 0L;
//						if (orderSum < 50_000) {
//							deliveryFee = 2500L;
//							System.out.println("");
//							System.out.printf("배송비: %d원 ", deliveryFee);
//						}
//						System.out.println("");
//						System.out.println("----------------------------");
//						System.out.printf("지불금액: %s원", orderSum + deliveryFee); // 지불금액 숫자 포맷 적용
//						System.out.println("");
//						System.out.println("----------------------------");
//						System.out.println("");
//					}
//					break;
//				}
//			}
//		} catch (IOException e) {
//		}
//	}
}