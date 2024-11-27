package pri.yqx.common.exception.enums;

public enum BussinessErrorEnum implements ErrorEnum {
    BUSINESS_ERROR(1001, "{0}"),
    SYSTEM_ERROR(1002, "系统出错了");

    private Integer code;
    private String errorMsg;

    public Integer getErrorCode() {
        return this.code;
    }

    public String getErrormsg() {
        return this.errorMsg;
    }

    private BussinessErrorEnum(final Integer code, final String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }
}