package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.config.LockProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class DbDistributedLock implements Lock, InitializingBean {

    private Logger logger = LoggerFactory.getLogger(DbDistributedLock.class);

    @Override
    public String lock(String lock, LockProperties lockProperties) {
        return null;
    }

    @Override
    public String unlock(String lock, LockProperties lockProperties) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
