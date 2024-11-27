

package pri.yqx.common.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private Integer code;
    private String errorMsg;

    public BusinessException() {
    }

    public BusinessException(String errorMsg) {
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.errorMsg = message;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.errorMsg = message;
    }

    public BusinessException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.errorMsg = message;
    }


}
