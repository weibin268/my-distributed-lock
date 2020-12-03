package com.zhuang.distributedlock.aspect;


import com.zhuang.distributedlock.annotation.DistributedLock;
import com.zhuang.distributedlock.annotation.LockKey;
import com.zhuang.distributedlock.lock.Lock;
import com.zhuang.distributedlock.config.LockProperties;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Component
@Aspect
@EnableConfigurationProperties(LockProperties.class)
public class DistributedLockAspect {

    private Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);

    @Autowired
    private Lock distributeLock;
    @Autowired
    private LockProperties lockProperties;

    @Pointcut("@annotation(com.zhuang.distributedlock.annotation.DistributedLock)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LockProperties lockProp = new LockProperties();
        BeanUtils.copyProperties(lockProperties, lockProp);
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        if (StringUtils.isNotBlank(distributedLock.lockPre())) {
            lockProp.setLockPre(distributedLock.lockPre());
        }
        if (distributedLock.retryCount() > 0) {
            lockProp.setRetryCount(distributedLock.retryCount());
        }
        if (distributedLock.expiredTime() > 0) {
            lockProp.setExpiredTime(distributedLock.expiredTime());
        }
        String key = distributedLock.key();
        //若存在@LockKey的入参，则将其设置为分布式锁的KEY
        String paramKey = paramToKey(method, proceedingJoinPoint.getArgs());
        if (StringUtils.isNotBlank(paramKey)) {
            key = key + paramKey;
        }
        //分布式锁没有key，则取其类名+方法名
        if (StringUtils.isEmpty(key)) {
            String className = proceedingJoinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            key = className + ":" + methodName;
        }
        try {
            String lockKey = distributeLock.lock(key, lockProp);
            Object object = proceedingJoinPoint.proceed();
            logger.debug("加锁业务执行成功，lockKey:{}，准备释放锁", lockKey);
            return object;
        } finally {
            distributeLock.unlock(key, lockProp);
        }
    }

    /**
     * 根据函数的入参及参数注解，将存在注解的参数拼接成字符串作为KEY
     *
     * @param method
     * @param args
     * @return
     */
    private String paramToKey(Method method, Object[] args) {
        StringBuilder sb = new StringBuilder();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < paramAnnotations.length; i++) {
            Annotation[] annotation = paramAnnotations[i];
            if (annotation.length > 0) {
                for (int j = 0; j < annotation.length; j++) {
                    if (annotation[j] instanceof LockKey) {
                        if (sb.length() > 0) {
                            sb.append(":");
                        }
                        sb.append(args[i]);
                    }
                }
            }
        }
        return sb.toString();
    }

}
