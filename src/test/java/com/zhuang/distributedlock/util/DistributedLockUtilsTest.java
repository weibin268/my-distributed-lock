package com.zhuang.distributedlock.util;

import com.zhuang.distributedlock.MyDistributedLockTestApplicationTest;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class DistributedLockUtilsTest extends MyDistributedLockTestApplicationTest {

    @Test
    public void test() throws InterruptedException {
        DistributedLockUtils.lock("test");
        TimeUnit.SECONDS.sleep(60);
    }

    @Test
    public void test2() throws InterruptedException {
        Object test = DistributedLockUtils.lock("test");
        System.out.println(test);
    }

}