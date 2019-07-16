package com.netease.music.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class BaseTest extends AbstractTestNGSpringContextTests {

    public BaseTest() {
        super();
    }
}
