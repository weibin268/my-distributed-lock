package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.config.LockProperties;

public interface Lock {

    String lock(String lock, LockProperties lockProperties);

    String unlock(String lock, LockProperties lockProperties);
}
