package com.jerodis.kr.co._29cm.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

// @SpringBootApplication
public class Application3 {

	public static void main(String[] args) throws IOException {
		// SpringApplication.run(Application.class, args);


		// 파일에 있는 초기 data setting
		File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "items.csv");

		List<String[]> itemList = Files.lines(file.toPath())
								.map(line -> line.split(","))
								.toList();

		Map<String, String[]> itemsMap = Files.lines(file.toPath())
								.map(line -> line.split(","))
								.collect(Collectors.toMap(p1 -> p1[0], p2 -> p2));

		// 콘솔 입력 (o or q)
		while(true) {
			System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String stdin = br.readLine();

			// 콘솔 입력 validation 체크
			// 콘솔 주문 상세 입력 (o or q)
			if (stdin.equals("o") || stdin.equals("order")) {
				for (int i = 0; i < itemList.size(); i++) {
					for (int j = 0; j < itemList.get(i).length; j++) {
						System.out.printf("%s     ", itemList.get(i)[j]);
					}
					System.out.println("");
				}
			} else if (stdin.equals("q") || stdin.equals("quit")) {
				return;
			}

			// 콘솔 입력 validation 체크 (null, 중복, 유효성(재고 수량 내, 1 이상))
			// 주문 상세 data 콘솔 출력
			List<String> itemNoList = new ArrayList<>();
			List<String> itemQuantityList = new ArrayList<>();

			System.out.println("");

			while (true) {
				System.out.print("상품번호: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				String itemNo = br.readLine();
				if (StringUtils.hasText(itemNo))
					itemNoList.add(itemNo);

				System.out.print("수량: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				String itemQuantity = br.readLine();
				if (StringUtils.hasText(itemQuantity))
					itemQuantityList.add(itemQuantity);

				// if (itemNo == null || itemQuantity == null) {
				if (!StringUtils.hasText(itemNo) && !StringUtils.hasText(itemQuantity)) {
					if (itemNoList.size() > 0 && itemQuantityList.size() > 0) {
						System.out.println("주문 내역: ");
						System.out.println("----------------------------");

						Long orderSum = 0L;
						for (int i = 0; i < itemNoList.size(); i++) {
							String orderNo = itemsMap.get(itemNoList.get(i))[1];
							Long orderAmount = Long.parseLong(itemsMap.get(itemNoList.get(i))[2]);
							Long orderQuantity = Long.parseLong(itemQuantityList.get(i));
							orderSum += (orderAmount * orderQuantity);

							System.out.printf("%s - %d개", orderNo, orderQuantity);
							System.out.println("");
						}

						System.out.println("----------------------------");
						System.out.printf("주문금액: %s원", orderSum); // 주문금액 숫자 포맷 적용
						Long deliveryFee = 0L;
						if (orderSum < 50_000) {
							deliveryFee = 2500L;
							System.out.println("");
							System.out.printf("배송비: %d원 ", deliveryFee);
						}
						System.out.println("");
						System.out.println("----------------------------");
						System.out.printf("지불금액: %s원", orderSum + deliveryFee); // 지불금액 숫자 포맷 적용
						System.out.println("");
						System.out.println("----------------------------");
						System.out.println("");
					}
					break;
				}
			}
		}
	}

}