package com.zhuang.distributedlock.exception;

public class LockTimeoutException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LockTimeoutException() {
        super();
    }

    public LockTimeoutException(String s) {
        super(s);
    }

    public LockTimeoutException(String s, Throwable cause) {
        super(s, cause);
    }
}
