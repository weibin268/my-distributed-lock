package com.zhuang.distributedlock.manager;


import com.zhuang.distributedlock.lock.Lock;
import com.zhuang.distributedlock.properties.LockProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
@EnableConfigurationProperties(LockProperties.class)
public class LuaRedisLockManager implements LockManager {

    private Logger logger = LoggerFactory.getLogger(LuaRedisLockManager.class);

    @Autowired
    private Lock distributeLock;
    @Autowired
    private LockProperties lockProperties;

    public void callBack(String lockKey, LockCallBack callBack) {
        Assert.notNull(lockKey,"lockKey can't not be null");
        Assert.notNull(callBack,"callBack can't not be null");
        try{
            //获取锁
            String key = distributeLock.lock(lockKey,lockProperties);
            //执行业务
            callBack.execute();
            logger.debug("加锁业务执行成功，lockKey:{}，准备释放锁",key);
        }finally{
            //释放锁
            distributeLock.unlock(lockKey,lockProperties);
        }
    }

    public <T> T callBack(String lockKey, ReturnCallBack<T> callBack) {
        Assert.notNull(lockKey,"lockKey can't not be null");
        Assert.notNull(callBack,"callBack can't not be null");
        try{
            //获取锁
            String key = distributeLock.lock(lockKey,lockProperties);
            //执行业务
            T t = callBack.execute();
            logger.debug("加锁业务执行成功，lockKey:{}，准备释放锁",key);
            return t;
        }finally{
            //释放锁
            distributeLock.unlock(lockKey,lockProperties);
        }
    }
}
