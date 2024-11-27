

package pri.yqx.chat.domain.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
@Data
@Accessors(chain = true)
public class ContactVo {
    private Long contactId;
    private String latestMsg;
    private LocalDateTime updateTime;
    private Integer noReadNum;
    private UserInfo userInfo;
    private GoodInfo goodInfo;


    @Data
    @Accessors(chain = true)
    public static class GoodInfo {
        private Long goodId;
        private PicUrl picUrl;


    }
    @Data
    @Accessors(chain = true)
    public static class UserInfo {
        private Long userId;
        private String profilePicUrl;
        private String userName;


    }
}
