package pri.yqx.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AiChat {

    @TableId
    private Long chatId;

    private Integer type;

    private Long roomId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    private String content;
    private String goods;

}