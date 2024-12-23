

package pri.yqx.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class Contact {
    @TableId
    private Long contactId;
    private Long userId;
    private Long otherId;
    private Integer noReadNum;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //更新时间,用于作为游标
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long upTimeStamp;
    private LocalDateTime earlistTime;
    private Long goodId;
    private String latestMsg;
    private Long roomKey;
    @Version
    private Integer version;
    private Boolean isDeleted;


}
