package com.zhuang.distributedlock.manager;


public interface ReturnCallBack<T> {
    T execute();
}
