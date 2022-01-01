package com.zhuang.distributedlock;

import com.zhuang.distributedlock.config.LockProperties;
import com.zhuang.distributedlock.lock.DatabaseDistributedLock;
import com.zhuang.distributedlock.lock.Lock;
import com.zhuang.distributedlock.lock.RedisDistributedLock;
import com.zhuang.distributedlock.lock.ZooKeeperDistributedLock;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@Configurable
@ComponentScan
public class AutoConfiguration {
    //此配置不可删除需要保留，否则集成到其它工程中，不会扫描spring的bean

    @Autowired
    private LockProperties lockProperties;

    @Bean
    public Lock lock() {
        Lock lock = null;
        if (lockProperties.getLockType().equals(LockProperties.LOCK_TYPE_REDIS)) {
            lock = new RedisDistributedLock();
        }
        if (lockProperties.getLockType().equals(LockProperties.LOCK_TYPE_ZOOKEEPER)) {
            lock = new ZooKeeperDistributedLock();
        }
        if (lockProperties.getLockType().equals(LockProperties.LOCK_TYPE_DATABASE)) {
            lock = new DatabaseDistributedLock();
        }
        return lock;
    }


    @Bean(initMethod = "start", destroyMethod = "close")
    @ConditionalOnProperty(prefix = LockProperties.PREFIX, name = "lock-type", havingValue = LockProperties.LOCK_TYPE_ZOOKEEPER)
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(Long.valueOf(lockProperties.getRetryInterval()).intValue(), lockProperties.getRetryCount());
        return CuratorFrameworkFactory.builder()
                .connectString(lockProperties.getZooKeeper().getServerLists())
                .namespace(lockProperties.getLockPre())
                .retryPolicy(retryPolicy)
                .build();
    }

}
