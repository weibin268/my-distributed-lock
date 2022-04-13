package com.zhuang.distributedlock.service;

import com.zhuang.distributedlock.MyDistributedLockTestApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SysDistributedLockServiceTest extends MyDistributedLockTestApplicationTest {

    @Autowired
    private SysDistributedLockService sysDistributedLockService;

    @Test
    public void test() {
        System.out.println(sysDistributedLockService.lock("test001", 100000));
    }

}
