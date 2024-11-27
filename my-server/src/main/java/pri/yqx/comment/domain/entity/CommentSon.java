//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class CommentSon {
    @TableId
    private Long sonCommentId;
    private Long commentId;
    private String content;
    private Long userId;
    private Long replyId;
    private Integer goodJobNum;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;


}
