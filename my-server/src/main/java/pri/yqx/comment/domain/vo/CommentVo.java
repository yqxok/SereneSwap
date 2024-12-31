//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.comment.domain.serializer.CreateTimeSerializer;
@Data
@Accessors(chain = true)
public class CommentVo {
    private Long commentId;
    private UserInfo userInfo;
    @JSONField(
        serialzeFeatures = {SerializerFeature.WriteNonStringValueAsString}
    )
    private String content;
    private Integer goodJobNum;
    private Boolean isGoodJob;
    private Boolean putAwayComment;
    private Integer initSize;
    private CmCursorPageVo<CommentSonVo> sonComments;
    @JSONField(serializeUsing = CreateTimeSerializer.class)
    private LocalDateTime createTime;


    @Data
    public static class UserInfo {
        private Long userId;
        private String profilePicUrl;
        private String userName;


    }
}
