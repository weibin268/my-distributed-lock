package com.zhuang.distributedlock.util;

import com.zhuang.distributedlock.MyDistributedLockTestApplicationTest;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class DistributedLockUtilsTest extends MyDistributedLockTestApplicationTest {

    @Test
    public void lock() throws InterruptedException {
        System.out.println("begin lock");
        Object lock = DistributedLockUtils.lock("test");
        TimeUnit.SECONDS.sleep(15);
        System.out.println("begin unlock");
        DistributedLockUtils.unlock(lock);
        TimeUnit.SECONDS.sleep(60);
    }

    @Test
    public void callBack() {
        DistributedLockUtils.callBack("test", () -> {
            System.out.println("done!!");
        });
    }

}
