package com.lj.testing.server.aspect;

import com.lj.testing.server.util.BaseJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DaoAspect {


    @Pointcut("execution(public * com.lj.testing.dao.mapper.*.*Mapper.*(..))")
    public void daoLog() {

    }

    @AfterReturning(returning = "ret", pointcut = "daoLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        log.info(BaseJsonUtils.writeValue(ret));
    }

}
