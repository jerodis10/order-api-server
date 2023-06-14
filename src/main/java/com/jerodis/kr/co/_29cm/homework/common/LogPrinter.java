package com.jerodis.kr.co._29cm.homework.common;

import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.Stock;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.numberFormatter;

@Slf4j
public class LogPrinter implements Printer {
    @Override
    public void print(String s) {
        log.info("{}", s);
    }

    @Override
    public void println(String s) {
        log.info("{}", s);
    }

    @Override
    public void print(List<Stock> stocks) {
        log.info("{}                    {}                             {}         {}",
                OrderColumn.ITEM_NO.getColumnName(), OrderColumn.ITEM_NAME.getColumnName(),
                OrderColumn.AMOUNT.getColumnName(), OrderColumn.QUANTITY.getColumnName());

        System.out.println(LONG_LINE_SEPARATOR);

        for (Stock stock : stocks) {
            log.info("{}     {}               {}         {}",
                    stock.getItemNo(), stock.getItemName(), numberFormatter(stock.getPrice()), numberFormatter(stock.getStockQuantity()));
        }
    }

    @Override
    public void initInput() {
        log.info("입력({}[{}]: {}, {}[{}]: {}) : ",
            OrderCommand.ORDER.getCommandChar(), OrderCommand.ORDER.getCommandString(), OrderCommand.ORDER.getCommandWord(),
            OrderCommand.QUIT.getCommandWord(), OrderCommand.QUIT.getCommandString(), OrderCommand.QUIT.getCommandWord());
    }

    @Override
    public void print(Order order) {
        println(Printer.orderDetailPrint(order));
    }
}
