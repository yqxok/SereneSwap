//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.exception;

import java.util.stream.Collectors;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pri.yqx.common.domain.response.Result;

@RestControllerAdvice
public class GlobalException {
    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    public GlobalException() {
    }

    @ExceptionHandler({SystemException.class})
    public Result<String> exception(SystemException systemException) {
        return Result.error(500, "系统出现异常");
    }

    @ExceptionHandler({BusinessException.class})
    public Result<String> exception(BusinessException businessException) {
        return Result.error(500, businessException.getErrorMsg());
    }

    @ExceptionHandler({BindException.class})
    public Result<String> exceptionHandler(BindException e) {
        String messages = (String)e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("；"));
        return Result.error(400, messages);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public Result<String> validException(ConstraintViolationException e) {
        String messages = (String)e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("；"));
        return Result.error(400, messages);
    }
}
