//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class OrderUnion {
    private Long orderId;
    private Long goodId;
    private String address;
    private String receiver;
    private String phoneNumber;
    private BigDecimal totalPrice;
    private Integer status;
    private Integer buyNum;
    private LocalDateTime createTime;
    private LocalDateTime dealTime;
    private Long orderInfoId;
    private Long userId;
    private Long dealUserId;
    private Boolean isBuyer;
    private Boolean isDeleted;

}
