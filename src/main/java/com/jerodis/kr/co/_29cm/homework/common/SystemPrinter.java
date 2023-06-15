package com.jerodis.kr.co._29cm.homework.common;

import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.Stock;

import java.util.List;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.numberFormatter;

public class SystemPrinter implements Printer {
    private static final String PRINT_ITEM_FORMAT = "%-10s %-50s %-20s %s\n";

    @Override
    public void print(String s) {
        System.out.print(s);
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public void print(List<Stock> stocks) {
        System.out.printf(PRINT_ITEM_FORMAT,
                OrderColumn.ITEM_NO.getColumnName(), OrderColumn.ITEM_NAME.getColumnName(),
                OrderColumn.AMOUNT.getColumnName(), OrderColumn.QUANTITY.getColumnName());

        System.out.println(LONG_LINE_SEPARATOR);

        for (Stock stock : stocks) {
            System.out.printf(PRINT_ITEM_FORMAT,
                    stock.getItemNo(), stock.getItemName(), numberFormatter(stock.getPrice()), numberFormatter(stock.getStockQuantity()));
        }
    }

    @Override
    public void initInput() {
        System.out.printf("입력(%s[%s]: %s, %s[%s]: %s) : ",
            OrderCommand.ORDER.getCommandChar(), OrderCommand.ORDER.getCommandString(), OrderCommand.ORDER.getCommandWord(),
            OrderCommand.QUIT.getCommandWord(), OrderCommand.QUIT.getCommandString(), OrderCommand.QUIT.getCommandWord());
    }

    @Override
    public void print(Order order) {
        println(Printer.orderDetailPrint(order));
    }

}
