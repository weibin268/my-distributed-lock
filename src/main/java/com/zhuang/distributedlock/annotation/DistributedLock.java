package com.zhuang.distributedlock.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DistributedLock {

    String key() default "";//锁的KEY

    String lockPre() default "";//锁的前缀

    int retryCount() default 0;//重试次数

    long expiredTime() default 0;//持锁时间 单位：毫秒

}
