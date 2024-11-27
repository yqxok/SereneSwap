//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
@Data
@Accessors(chain = true)
public class GoodVo {
    private Long goodId;
    private String html;
    private PicUrl picUrl;
    private BigDecimal price;
    private UserInfo userInfo;


    @Data
    @Accessors(chain = true)
    public static class UserInfo {
        private Long userId;
        private String profilePicUrl;
        private String userName;

    }
}
