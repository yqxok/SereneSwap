//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class OrderMsg {
    @TableId
    private Long orderMsgId;
    private Long senderId;
    private Long receiverId;
    private Long orderId;
    private Long goodId;
    private Integer status;
    private Boolean isRead;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;


}
