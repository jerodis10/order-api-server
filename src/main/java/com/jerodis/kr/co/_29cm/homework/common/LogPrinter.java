package com.jerodis.kr.co._29cm.homework.common;

import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.service.OrderColumn;
import com.jerodis.kr.co._29cm.homework.service.OrderCommand;
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
    public void print(List<Item> itemList) {
        log.info("{}                    {}                             {}         {}",
                OrderColumn.ITEM_NO.columnName, OrderColumn.ITEM_NAME.columnName,
                OrderColumn.AMOUNT.columnName, OrderColumn.QUANTITY.columnName);

        System.out.println("------------------------------------------------------------------------------------------");

        for (Item item : itemList) {
            log.info("{}     {}               {}         {}",
                    item.getItemNo(), item.getItemName(), numberFormatter(item.getPrice()), numberFormatter(item.getQuantity()));
        }
    }

    @Override
    public void initInput() {
        log.info("입력({}[{}]: {}, {}[{}]: {}) : ",
            OrderCommand.ORDER.commandChar, OrderCommand.ORDER.commandString, OrderCommand.ORDER.commandWord,
            OrderCommand.QUIT.commandChar, OrderCommand.QUIT.commandString, OrderCommand.QUIT.commandWord);
    }

    @Override
    public void print(Order order) {
        println(Printer.orderDetailPrint(order));
    }
}
