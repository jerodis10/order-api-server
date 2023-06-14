package com.jerodis.kr.co._29cm.homework.repository;

import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Stock;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Stock> findAllItem();

    Stock findStockByItemNo(String itemNo);
}
