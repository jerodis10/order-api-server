package com.jerodis.kr.co._29cm.homework.repository;

import com.jerodis.kr.co._29cm.homework.common.FileReader;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Stock;
import com.jerodis.kr.co._29cm.homework.exception.CommonException;
import com.jerodis.kr.co._29cm.homework.exception.CommonExceptionStatus;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class FileOrderRepository implements OrderRepository{

    private static final Long SKIP_ROWNUM = 1L;

    private final Map<Long, Stock> stockMap;

    public FileOrderRepository(Map<Long, Stock> stockMap) {
        this.stockMap = stockMap;
    }

    public static FileOrderRepository init() {
        Map<Long, Stock> map = new HashMap<>();
        List<Stock> stocks = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader();
            File file = fileReader.read();

            List<String[]> fileList = Files.lines(file.toPath())
                    .skip(SKIP_ROWNUM)
                    .map(line -> line.split(","))
                    .toList();

            stocks = Stock.toListStock(fileList);

            for (Stock stock : stocks) {
                map.put(stock.getItemNo(), stock);
            }

        } catch (IOException e) {
            throw new CommonException(CommonExceptionStatus.IO_EXCEPTION);
        }

        return new FileOrderRepository(map);
    }

    @Override
    public List<Stock> findAllItem() {
        return new ArrayList<>(stockMap.values());

//        List<Stock> result = new ArrayList<>();
//
//        try {
//            FileReader fileReader = new FileReader();
//            File file = fileReader.read();
//
//            List<String[]> fileList = Files.lines(file.toPath())
//                    .skip(SKIP_ROWNUM)
//                    .map(line -> line.split(","))
//                    .toList();
//
//            result = Stock.toListStock(fileList);
//
//        } catch (IOException e) {
//            throw new CommonException(CommonExceptionStatus.IO_EXCEPTION);
//        }
//
//        return result;
    }

    @Override
    public Stock findStockByItemNo(String itemNo) {
        Stock stock = stockMap.get(Long.parseLong(itemNo));
        if (stock == null) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, itemNo);
        }

        return stock;


//        Stock stock = null;
//
//        try {
//            FileReader fileReader = new FileReader();
//            File file = fileReader.read();

//            String[] line = Files.lines(file.toPath())
//                    .skip(SKIP_ROWNUM)
//                    .map(m -> m.split(","))
//                    .filter(p -> p[0].equals(itemNo))
//                    .findAny()
//                    .orElse(null);
//                    .orElseThrow(() -> new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, itemNo));

//            if(line == null) return null;
//
//            stock = Stock.toStock(line);
//        } catch (IOException e) {
//            throw new CommonException(CommonExceptionStatus.IO_EXCEPTION);
//        }

//        return stockMap.get(stock.getItemNo());
    }

}
