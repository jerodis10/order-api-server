package kr.co._29cm.homework;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;

public class BaseTest {
    protected static final Long MINIMUM_SHIPPING_AMOUNT = 50_000L;
    protected static final Long BASIC_SHIPPING_AMOUNT = 0L;
    protected static final Long SHIPPING_AMOUNT = 2_500L;

    protected static final FixtureMonkey sut = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .build();

}
