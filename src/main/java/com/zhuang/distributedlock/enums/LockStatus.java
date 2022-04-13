package com.zhuang.distributedlock.enums;

public enum LockStatus {

    RELEASED(0, "已释放"),
    LOCKED(1, "已锁定");

    LockStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    private Integer value;
    private String name;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
