package com.jerodis.kr.co._29cm.homework.repository;

import com.jerodis.kr.co._29cm.homework.domain.Stock;

import java.util.List;

public interface OrderRepository {

    List<Stock> findAllItem();

    Stock findStockByItemNo(String itemNo);
}
