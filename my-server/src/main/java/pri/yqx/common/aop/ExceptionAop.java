//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.yqx.common.exception.BusinessException;
import pri.yqx.common.exception.SystemException;

@Component
@Aspect
public class ExceptionAop {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAop.class);

    public ExceptionAop() {
    }

    @Pointcut("execution(* pri.yqx.*.service.impl.*.*(..))")
    public void pointCut() {
    }

    @AfterThrowing(
        value = "pointCut()",
        throwing = "exception"
    )
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        if (exception instanceof BusinessException) {
            throw (BusinessException)exception;
        } else {
            Signature signature = joinPoint.getSignature();
            String msg = signature.getDeclaringTypeName() + " " + signature.getName() + "出错了";
            log.error(msg, exception);
            throw new SystemException(exception);
        }
    }
}
