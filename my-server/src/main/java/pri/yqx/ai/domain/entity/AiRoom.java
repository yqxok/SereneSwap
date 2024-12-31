package pri.yqx.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AiRoom {

    @TableId
    private Long roomId;

    private Long userId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;
    private String roomName;
    private Boolean isDeleted;
}