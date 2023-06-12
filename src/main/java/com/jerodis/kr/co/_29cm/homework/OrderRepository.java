package com.jerodis.kr.co._29cm.homework;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Item> findAllItem();

    Optional<Item> findOneItem(Long itemNo);
}
