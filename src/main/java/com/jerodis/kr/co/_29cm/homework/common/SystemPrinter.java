package com.jerodis.kr.co._29cm.homework.common;

import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.service.OrderColumn;
import com.jerodis.kr.co._29cm.homework.service.OrderCommand;

import java.util.List;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.numberFormatter;

public class SystemPrinter implements Printer {
    private static final String PRINT_ITEM_FORMAT = "%-10s %-50s %-10s %s\n";

    @Override
    public void print(String s) {
        System.out.print(s);
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public void print(List<Item> itemList) {
        System.out.printf(PRINT_ITEM_FORMAT,
                OrderColumn.ITEM_NO.columnName, OrderColumn.ITEM_NAME.columnName, OrderColumn.AMOUNT.columnName, OrderColumn.QUANTITY.columnName);

        System.out.println(LONG_LINE_SEPARATOR);
//        System.out.println("------------------------------------------------------------------------------------------");

        for (Item item : itemList) {
            System.out.printf(PRINT_ITEM_FORMAT,
                    item.getItemNo(), item.getItemName(), numberFormatter(item.getPrice()), numberFormatter(item.getQuantity()));
        }
    }

    @Override
    public void initInput() {
        System.out.printf("입력(%s[%s]: %s, %s[%s]: %s) : ",
            OrderCommand.ORDER.commandChar, OrderCommand.ORDER.commandString, OrderCommand.ORDER.commandWord,
            OrderCommand.QUIT.commandChar, OrderCommand.QUIT.commandString, OrderCommand.QUIT.commandWord);
    }

    @Override
    public void print(Order order) {
        println(Printer.orderDetailPrint(order));
    }

}
