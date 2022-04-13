package com.zhuang.distributedlock.manager;


public interface LockManager {

    void callBack(String lockKey, LockCallBack callBack);

    <T> T callBack(String lockKey, ReturnCallBack<T> callBack);
}
