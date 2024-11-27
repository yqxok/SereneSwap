
package pri.yqx.user.domain.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.good.domain.vo.GoodVo;
@Data
@Accessors(chain = true)
public class CollectVo {
    private Long collectId;
    private GoodVo.UserInfo userInfo;
    private GoodInfo goodInfo;
    private Boolean choosed = false;
    private LocalDateTime createTime;

    @Data
    public static class GoodInfo {
        private Long goodId;
        private String html;
        private PicUrl picUrl;
        private BigDecimal price;
        private Short status;


    }
}
