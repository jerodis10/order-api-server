package com.jerodis.kr.co._29cm.homework;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileReader {

    private static final String FILE_NAME = "items.csv";

    public File read() throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + FILE_NAME);
    }

}
