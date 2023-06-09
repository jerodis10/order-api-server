package com.jerodis.kr.co._29cm.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Order {

	String itemNo;

	String itemName;

	Long amount;

	Long quantity;


}
