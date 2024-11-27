//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class MsgRoom {
    @TableId
    private Long msgRoomId;
    private Integer noReadNum;
    private Integer type;
    private Long userId;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;
    @TableField(
        fill = FieldFill.INSERT_UPDATE
    )
    private LocalDateTime updateTime;
    @Version
    private Integer version;


}
