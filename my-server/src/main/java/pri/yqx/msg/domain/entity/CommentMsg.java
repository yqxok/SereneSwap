//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class CommentMsg {
    @TableId
    private Long cmMsgId;
    private Long commentId;
    private Long goodId;
    private String content;
    private Boolean isRead;
    private Long senderId;
    private Long receiverId;
    private Integer type;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;
    @TableLogic
    private Boolean isDeleted;


}
