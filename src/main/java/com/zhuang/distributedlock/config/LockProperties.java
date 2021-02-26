package com.zhuang.distributedlock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "my.distributed-lock")
public class LockProperties {

    private String lockPre = "lock";//锁key的前缀
    private long expiredTime = 60000;// 加锁操作持有锁的最大时间（单位：毫秒）
    private int retryCount = 100;//获取锁的重试次数
    private long retryInterval = 300;//获取锁的重试间隔时间（单位：毫秒）

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getLockPre() {
        return lockPre;
    }

    public void setLockPre(String lockPre) {
        this.lockPre = lockPre;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }

}
