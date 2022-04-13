package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.config.LockProperties;
import com.zhuang.distributedlock.service.SysDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DatabaseDistributedLock implements Lock<String>, InitializingBean {

    @Autowired
    private SysDistributedLockService sysDistributedLockService;

    @Override
    public String lock(String lock, LockProperties lockProperties) {
        Integer tryCount = lockProperties.getRetryCount();
        try {
            for (int i = 0; i < tryCount; i++) {
                boolean lockResult = sysDistributedLockService.lock(lock, lockProperties.getExpiredTime());
                if (lockResult) {
                    return lock;
                }
                Thread.sleep(lockProperties.getRetryInterval());
            }
            throw new RuntimeException("DatabaseDistributedLock.lock acquire lock timeout!");
        } catch (Exception e) {
            throw new RuntimeException("DatabaseDistributedLock.lock fail!", e);
        }
    }

    @Override
    public boolean unlock(String lock, LockProperties lockProperties) {
        return sysDistributedLockService.unlock(lock);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
