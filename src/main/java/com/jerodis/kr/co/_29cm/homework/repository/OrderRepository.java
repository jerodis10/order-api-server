package com.jerodis.kr.co._29cm.homework.repository;

import com.jerodis.kr.co._29cm.homework.domain.Item;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Item> findAllItem();

    Optional<Item> findOneItem(Long itemNo);
}
