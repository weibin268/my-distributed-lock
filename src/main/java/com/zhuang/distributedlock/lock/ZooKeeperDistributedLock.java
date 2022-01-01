package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.config.LockProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.common.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ZooKeeperDistributedLock implements Lock<InterProcessMutex>, InitializingBean {

    @Autowired
    private CuratorFramework client;

    @Override
    public InterProcessMutex lock(String lockKey, LockProperties lockProperties) {
        Assert.notNull(lockKey, "lockKey can't be null");
        if (!lockKey.startsWith("/")) {
            lockKey = "/" + lockKey;
        }
        InterProcessMutex lock = new InterProcessMutex(client, lockKey);
        try {
            boolean acquireResult = lock.acquire(lockProperties.getAcquireTimeout(), TimeUnit.MILLISECONDS);
            if (!acquireResult) {
                throw new RuntimeException("ZkDistributedLock.lock acquire lock timeout!");
            }
        } catch (Exception e) {
            throw new RuntimeException("ZkDistributedLock.lock fail!", e);
        }
        return lock;
    }

    @Override
    public boolean unlock(InterProcessMutex lock, LockProperties lockProperties) {
        Assert.notNull(lock, "lock can't be null");
        try {
            lock.release();
        } catch (Exception e) {
            throw new RuntimeException("ZkDistributedLock.unlock fail!", e);
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() {

    }
}
