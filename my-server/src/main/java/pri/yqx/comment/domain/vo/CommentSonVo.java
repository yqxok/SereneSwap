//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.comment.domain.serializer.CreateTimeSerializer;
import pri.yqx.good.domain.vo.GoodVo;
@Data
@Accessors(chain = true)
public class CommentSonVo {
    private Long sonCommentId;
    private String content;
    private String replyName;
    private GoodVo.UserInfo userInfo;
    private Integer goodJobNum;
    private Boolean isGoodJob;
    @JSONField(
        serializeUsing = CreateTimeSerializer.class
    )
    private LocalDateTime createTime;


}
