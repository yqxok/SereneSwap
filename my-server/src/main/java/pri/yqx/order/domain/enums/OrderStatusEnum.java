
package pri.yqx.order.domain.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum OrderStatusEnum {
    DEALING(0, "您的宝贝已被拍下"),
    DEALED(1, "您的订单已完成"),
    CANCELDEAL(2, "您的订单已被买家取消");

    private static Map<Integer, String> cache = Arrays.stream(values()).collect(Collectors.toMap(OrderStatusEnum::getStatus, OrderStatusEnum::getMsg));
    private final Integer status;
    private final String msg;

    private OrderStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsgWithStatus(Integer status) {
        return (String)cache.get(status);
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }
}
