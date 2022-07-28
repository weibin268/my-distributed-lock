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
        _this.lockManager.callBack(lockKey, _this.lockProperties, callBack);
    }

    public static void callBack(String lockKey, LockProperties lockProperties, LockCallBack callBack) {
        _this.lockManager.callBack(lockKey, lockProperties, callBack);
    }

    public static <T> T callBack(String lockKey, ReturnCallBack<T> callBack) {
        return _this.lockManager.callBack(lockKey, _this.lockProperties, callBack);
    }

    public static <T> T callBack(String lockKey, LockProperties lockProperties, ReturnCallBack<T> callBack) {
        return _this.lockManager.callBack(lockKey, lockProperties, callBack);
    }

    /**
     * @param lockKey 锁的key
     * @return 锁
     */
    public static Object lock(String lockKey) {
        return lock(lockKey, _this.lockProperties);
    }

    public static Object lock(String lockKey, LockProperties lockProperties) {
        return _this.lock.lock(lockKey, lockProperties);
    }

    /**
     * @param lock 锁
     * @return 结果
     */
    public static boolean unlock(Object lock) {
        return unlock(lock, _this.lockProperties);
    }

    public static boolean unlock(Object lock, LockProperties lockProperties) {
        return _this.lock.unlock(lock, lockProperties);
    }
}
