package kr.co._29cm.homework.repository;

import kr.co._29cm.homework.common.FileReader;
import kr.co._29cm.homework.domain.Stock;
import kr.co._29cm.homework.exception.CommonException;
import kr.co._29cm.homework.exception.CommonExceptionStatus;
import kr.co._29cm.homework.exception.InvalidCommandException;
import kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileOrderRepository implements OrderRepository{

    private static final Long SKIP_ROWNUM = 1L;

    public static Map<Long, Stock> stockMap;

    public FileOrderRepository(Map<Long, Stock> stockMap) {
        FileOrderRepository.stockMap = stockMap;
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
    }

    @Override
    public Stock findStockByItemNo(String itemNo) {
        Stock stock = stockMap.get(Long.parseLong(itemNo));
        if (stock == null) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, itemNo);
        }

        return stock;
    }

}
