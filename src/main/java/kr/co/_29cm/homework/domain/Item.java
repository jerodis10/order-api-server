package kr.co._29cm.homework.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Item {

	@NonNull
	private final Long itemNo;

	@NonNull
	private final String itemName;

	@NonNull
	private final Long price;


	@Builder
	public Item(Long itemNo, String itemName, Long price) {
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.price = price;
	}

}
