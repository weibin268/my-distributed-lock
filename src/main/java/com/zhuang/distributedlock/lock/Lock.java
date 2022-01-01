package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.config.LockProperties;

public interface Lock<T> {

    T lock(String lockKey, LockProperties lockProperties);

    boolean unlock(T lock, LockProperties lockProperties);
}
