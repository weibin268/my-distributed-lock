package com.zhuang.distributedlock.manager;


import com.zhuang.distributedlock.config.LockProperties;

public interface LockManager {

    void callBack(String lockKey, LockProperties lockProperties, LockCallBack callBack);

    <T> T callBack(String lockKey,LockProperties lockProperties, ReturnCallBack<T> callBack);
}
