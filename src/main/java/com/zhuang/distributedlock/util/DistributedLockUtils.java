package com.zhuang.distributedlock.util;

import com.zhuang.distributedlock.config.LockProperties;
import com.zhuang.distributedlock.lock.Lock;
import com.zhuang.distributedlock.manager.LockCallBack;
import com.zhuang.distributedlock.manager.LockManager;
import com.zhuang.distributedlock.manager.ReturnCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 分布式锁工具类
 */
@Component
public class DistributedLockUtils {

    private static DistributedLockUtils _this;

    @Autowired
    private LockManager lockManager;
    @Autowired
    private Lock lock;

    @PostConstruct
    public void init() {
        _this = this;
    }

    public static void callBack(String lockKey, LockCallBack callBack) {
        _this.lockManager.callBack(lockKey, callBack);
    }

    public static <T> T callBack(String lockKey, ReturnCallBack<T> callBack) {
        return _this.lockManager.callBack(lockKey, callBack);
    }

    public String lock(String lockKey, long expiredTime) {
        LockProperties lockConfig = new LockProperties();
        lockConfig.setExpiredTime(expiredTime);
        return lock(lockKey, lockConfig);
    }

    public String lock(String lockKey, LockProperties lockConfig) {
        return _this.lock.lock(lockKey, lockConfig);
    }
}
