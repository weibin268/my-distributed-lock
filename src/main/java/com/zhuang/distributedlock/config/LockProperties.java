package com.zhuang.distributedlock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.zhuang.distributedlock.config.LockProperties.PREFIX;


@Data
@Configuration
@ConfigurationProperties(prefix = PREFIX)
public class LockProperties {

    public static final String PREFIX = "my.distributed-lock";
    public static final String LOCK_TYPE_REDIS = "redis";
    public static final String LOCK_TYPE_ZOOKEEPER = "zookeeper";
    public static final String LOCK_TYPE_DATABASE = "database";
    private String lockType = LOCK_TYPE_REDIS;//锁类型
    private String lockPre = "my-distributed-lock";//锁key的前缀
    private long expiredTime = 60000;// 加锁操作持有锁的最大时间（单位：毫秒）
    private long acquireTimeout = 60000;// 获取锁动超时时间（单位：毫秒）
    private int retryCount = 100;//获取锁的重试次数
    private long retryInterval = 300;//获取锁的重试间隔时间（单位：毫秒）

    private ZooKeeper zooKeeper = new ZooKeeper();

    @Data
    public static class ZooKeeper {
        private String serverLists = "127.0.0.1:2181";
    }
}
