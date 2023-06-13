package com.jerodis.kr.co._29cm.homework.repository;

import com.jerodis.kr.co._29cm.homework.common.FileReader;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.exception.CommonException;
import com.jerodis.kr.co._29cm.homework.exception.CommonExceptionStatus;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileOrderRepository implements OrderRepository{

    private static final Long SKIP_ROWNUM = 1L;

    @Override
    public List<Item> findAllItem() {
        List<Item> result = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader();
            File file = fileReader.read();

            List<String[]> fileList = Files.lines(file.toPath())
                    .skip(SKIP_ROWNUM)
                    .map(line -> line.split(","))
                    .toList();

            result = Item.toListItem(fileList);

        } catch (IOException e) {
            throw new CommonException(CommonExceptionStatus.IO_EXCEPTION);
        }

        return result;
    }

    @Override
    public Optional<Item> findOneItem(String itemNo) {
        Item item = null;

        try {
            FileReader fileReader = new FileReader();
            File file = fileReader.read();

            String[] line = Files.lines(file.toPath())
                    .skip(SKIP_ROWNUM)
                    .map(m -> m.split(","))
                    .filter(p -> p[0].equals(itemNo))
                    .findAny()
                    .orElseThrow(() -> new InvalidCommandException(InvalidCommandExceptionStatus.ITEM_NOT_FOUND, itemNo));

            item = Item.toItem(line);

        } catch (IOException e) {
            throw new CommonException(CommonExceptionStatus.IO_EXCEPTION);
        }

        return Optional.ofNullable(item);
    }

}
