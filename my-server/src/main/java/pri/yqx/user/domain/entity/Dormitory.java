
package pri.yqx.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class Dormitory {
    @TableId
    private Long dormitoryId;
    private String dormiName;
    private String school;
    private String zone;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;
    @TableField(
        fill = FieldFill.INSERT_UPDATE
    )
    private LocalDateTime updateTime;
    private Integer version;
    @TableLogic
    private Boolean isDeleted;


}
