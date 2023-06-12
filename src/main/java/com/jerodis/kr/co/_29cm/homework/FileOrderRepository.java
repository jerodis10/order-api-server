package com.jerodis.kr.co._29cm.homework;

import java.io.File;
import java.io.FileNotFoundException;
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
                    .skip(1)
                    .map(line -> line.split(","))
                    .toList();

            result = Item.toListItem(fileList);

        } catch (IOException e) {
        }

        return result;
    }

    @Override
    public Optional<Item> findOneItem(Long itemNo) {
        Item item = null;

        try {
            FileReader fileReader = new FileReader();
            File file = fileReader.read();

            String[] line = Files.lines(file.toPath())
                    .skip(1)
                    .map(m -> m.split(","))
                    .filter(p -> p[0].equals(String.valueOf(itemNo)))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException());

            String itemName = "";
            int index = 0;

            for (int i = 1; i < line.length; i++) {
                if (isNumeric(line[i])) {
                    index = i;
                    break;
                } else {
                    itemName += line[i];
                }
            }


            item = Item.builder()
                    .itemNo(Long.valueOf(line[0]))
                    .itemName(itemName)
                    .price(Long.valueOf(line[index]))
                    .quantity(Long.valueOf(line[index + 1]))
                    .build();

        } catch (IOException e) {

        }

        return Optional.ofNullable(item);
    }

    private static boolean isNumeric(String s)
    {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
