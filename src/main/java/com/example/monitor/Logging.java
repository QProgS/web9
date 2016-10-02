package com.example.monitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.example.web.*.*(..))")
    public void monitor() {}

    @Around("monitor()")
    public Object profile(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        //logger.debug("JVM memory in use = "+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        Object output = null;
        try {
            output = pjp.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        long elapsedTime = System.currentTimeMillis() - start;
        logger.debug("Time: " + elapsedTime + " ms. " + pjp.getTarget()+"."+pjp.getSignature());

        return output;
    }
}
