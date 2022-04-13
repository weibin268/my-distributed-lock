package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.config.LockProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class DatabaseDistributedLock implements Lock<String>, InitializingBean {

    @Override
    public String lock(String lock, LockProperties lockProperties) {
        return null;
    }

    @Override
    public boolean unlock(String lock, LockProperties lockProperties) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
