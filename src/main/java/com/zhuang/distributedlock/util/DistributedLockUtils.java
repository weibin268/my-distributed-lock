package com.zhuang.distributedlock.util;

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
}
