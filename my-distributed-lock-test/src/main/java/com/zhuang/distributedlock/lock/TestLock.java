package com.zhuang.distributedlock.lock;


import java.text.SimpleDateFormat;
import java.util.Date;

public class TestLock {

    private Integer count = 0;
    public void test() {
        System.out.println("test -> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " -> " + count++);
    }

}
