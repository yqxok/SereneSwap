
package pri.yqx.order.domain.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
@Data
@Accessors(chain = true)
public class OrderVo {
    private Long orderId;
    private UserInfo dealUser;
    private GoodInfo goodInfo;
    private Integer buyNum;
    private BigDecimal totalPrice;
    private LocalDateTime createTime;
    private LocalDateTime dealTime;
    private Integer status;

    @Data
    @Accessors(chain = true)
    public static class GoodInfo {
        private Long goodId;
        private String html;
        private PicUrl picUrl;
        private BigDecimal price;
        private Integer status;


    }
    @Data
    @Accessors(chain = true)
    public static class UserInfo {
        private Long userId;
        private String profilePicUrl;
        private String userName;


    }
}
