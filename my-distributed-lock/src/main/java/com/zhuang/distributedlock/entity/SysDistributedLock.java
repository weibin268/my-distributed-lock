package com.zhuang.distributedlock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysDistributedLock {

    @TableId
    private String lockKey;

    private Integer lockStatus;

    private Date createTime;

    private Date beginTime;

    private Date endTime;

}
