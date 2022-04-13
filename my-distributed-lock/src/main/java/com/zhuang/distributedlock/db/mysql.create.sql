DROP TABLE IF EXISTS `sys_distributed_lock`;
CREATE TABLE `sys_distributed_lock`
(
    `key`         varchar(50)  NOT NULL COMMENT '锁的key',
    `status`      tinyint(4) NULL DEFAULT NULL COMMENT '状态：0=已释放；1=已锁定；',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `begin_time`  datetime NULL DEFAULT NULL COMMENT '锁定时间',
    `end_time`    datetime NULL DEFAULT NULL COMMENT '失效时间',
    PRIMARY KEY (`key`) USING BTREE
);
