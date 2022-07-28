package com.zhuang.distributedlock.manager;


import com.zhuang.distributedlock.lock.Lock;
import com.zhuang.distributedlock.config.LockProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
@EnableConfigurationProperties(LockProperties.class)
public class DefaultLockManager implements LockManager {

    private Logger logger = LoggerFactory.getLogger(DefaultLockManager.class);
    @Autowired
    private Lock distributeLock;

    public void callBack(String lockKey, LockProperties lockProperties, LockCallBack callBack) {
        Assert.notNull(lockKey, "lockKey can't not be null");
        Assert.notNull(callBack, "callBack can't not be null");
        Object lock = null;
        try {
            //获取锁
            lock = distributeLock.lock(lockKey, lockProperties);
            //执行业务
            callBack.execute();
            logger.debug("加锁业务执行成功，lockKey:{}，准备释放锁", lockKey);
        } finally {
            //释放锁
            if (lock != null) {
                distributeLock.unlock(lock, lockProperties);
            }
        }
    }

    public <T> T callBack(String lockKey, LockProperties lockProperties, ReturnCallBack<T> callBack) {
        Assert.notNull(lockKey, "lockKey can't not be null");
        Assert.notNull(callBack, "callBack can't not be null");
        Object lock = null;
        try {
            //获取锁
            lock = distributeLock.lock(lockKey, lockProperties);
            //执行业务
            T t = callBack.execute();
            logger.debug("加锁业务执行成功，lockKey:{}，准备释放锁", lockKey);
            return t;
        } finally {
            //释放锁
            if (lock != null) {
                distributeLock.unlock(lock, lockProperties);
            }
        }
    }
}
