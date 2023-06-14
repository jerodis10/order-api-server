package com.jerodis.kr.co._29cm.homework.domain;

import lombok.*;

//@Data
//@Value
@Getter
//@Setter
@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Value
public class Ord {
    @NonNull
    private final Long id;

    @NonNull
    private final String productName;


    @Builder
    public Ord(Long id, String productName) {
        this.id = id;
        this.productName = productName;
    }


//    @Builder
//    public Ord(Long id, String productName) {
//        this.id = id;
//        this.productName = productName;
//    }
}
