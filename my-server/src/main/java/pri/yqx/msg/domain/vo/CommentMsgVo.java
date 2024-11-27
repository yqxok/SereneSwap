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
public class CommentMsgVo {
    private Long cmMsgId;
    private Long commentId;
    private UserInfo userInfo;
    private GoodInfo goodInfo;
    private LocalDateTime createTime;
    private String content;
    private String type;


    @Data
    public static class GoodInfo {
        private Long goodId;
        private PicUrl picUrl;

    }
    @Data
    public static class UserInfo {
        private Long userId;
        private String profilePicUrl;
        private String userName;


    }
}
