package com.zhuang.distributedlock.lock;


import com.zhuang.distributedlock.properties.LockProperties;

public interface Lock {

    String lock(String lock, LockProperties lockProperties);

    String unlock(String lock, LockProperties lockProperties);
}
