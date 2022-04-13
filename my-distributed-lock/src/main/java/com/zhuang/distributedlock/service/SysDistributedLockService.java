package com.zhuang.distributedlock.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.distributedlock.entity.SysDistributedLock;
import com.zhuang.distributedlock.enums.LockStatus;
import com.zhuang.distributedlock.mapper.SysDistributedLockMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysDistributedLockService extends ServiceImpl<SysDistributedLockMapper, SysDistributedLock> {

    public boolean lock(String lockKey, long expiredTime) {
        Date now = new Date();
        Date endTime = new Date(now.getTime() + expiredTime);
        boolean isAdd = false;
        SysDistributedLock sysDistributedLock = getById(lockKey);
        if (sysDistributedLock == null) {
            isAdd = true;
            sysDistributedLock = new SysDistributedLock();
            sysDistributedLock.setLockKey(lockKey);
            sysDistributedLock.setLockStatus(LockStatus.LOCKED.getValue());
            sysDistributedLock.setCreateTime(now);
            sysDistributedLock.setBeginTime(now);
            sysDistributedLock.setEndTime(endTime);
        }

        sysDistributedLock.setLockStatus(LockStatus.LOCKED.getValue());
        sysDistributedLock.setBeginTime(now);
        sysDistributedLock.setEndTime(endTime);

        if (isAdd) {
            return save(sysDistributedLock);
        } else {
            return update(sysDistributedLock, new LambdaQueryWrapper<SysDistributedLock>()
                    .eq(SysDistributedLock::getLockKey, lockKey)
                    .and(c -> c.eq(SysDistributedLock::getLockStatus, LockStatus.RELEASED.getValue()).or().lt(SysDistributedLock::getEndTime, now))
            );
        }
    }

    public boolean unlock(String lockKey) {
        return update(new LambdaUpdateWrapper<SysDistributedLock>()
                .eq(SysDistributedLock::getLockKey, lockKey)
                .set(SysDistributedLock::getLockStatus, LockStatus.RELEASED.getValue())
        );
    }
}
