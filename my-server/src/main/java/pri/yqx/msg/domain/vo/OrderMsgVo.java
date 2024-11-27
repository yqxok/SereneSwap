//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
@Data
@Accessors(chain = true)
public class OrderMsgVo {
    private Long orderMsgId;
    private Long orderId;
    private String status;
    private LocalDateTime createTime;
    private GoodInfo goodInfo;
    private UserInfo userInfo;


    @Data
    public static class UserInfo {
        private Long userId;
        private String profilePicUrl;
        private String userName;


    }
    @Data
    public static class GoodInfo {
        private Long goodId;
        private PicUrl picUrl;
        private String html;

    }
}
