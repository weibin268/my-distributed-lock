package com.zhuang.distributedlock.util;

import com.zhuang.distributedlock.config.LockProperties;
import com.zhuang.distributedlock.lock.Lock;
import com.zhuang.distributedlock.manager.LockCallBack;
import com.zhuang.distributedlock.manager.LockManager;
import com.zhuang.distributedlock.manager.ReturnCallBack;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private LockProperties lockProperties;

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


    /**
     * @param lockKey 锁的key
     * @return
     */
    public static Object lock(String lockKey) {
        LockProperties lockConfig = new LockProperties();
        BeanUtils.copyProperties(_this.lockProperties, lockConfig);
        return _this.lock(lockKey, lockConfig);
    }

    /**
     * @param lockKey     锁的key
     * @param expiredTime 锁的过去时间，单位：毫秒
     * @return
     */
    public static Object lock(String lockKey, long expiredTime) {
        LockProperties lockConfig = new LockProperties();
        BeanUtils.copyProperties(_this.lockProperties, lockConfig);
        lockConfig.setExpiredTime(expiredTime);
        return _this.lock(lockKey, lockConfig);
    }

    public static Object lock(String lockKey, LockProperties lockConfig) {
        return _this.lock.lock(lockKey, lockConfig);
    }

}
