package com.jerodis.kr.co._29cm.homework;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

        System.out.println("------------------------------------------------------------------------------------------");

        for (Item item : itemList) {
            System.out.printf(PRINT_ITEM_FORMAT, item.getItemNo(), item.getItemName(), item.getPrice(), item.getQuantity());
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
        println(Printer.orderString(order));
    }

}
