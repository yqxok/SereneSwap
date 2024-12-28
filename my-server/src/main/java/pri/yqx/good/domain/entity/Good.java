//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.good.domain.json.PicUrlJsonHandler;

@TableName(autoResultMap = true)
@Data
@Accessors(chain = true)
public class Good {
    @TableId
    private Long goodId;
    private String html;
    @TableField(typeHandler = PicUrlJsonHandler.class)
    private List<PicUrl> picUrls;
    private BigDecimal price;
    private Integer status;
    private Integer browserTimes;
    private Integer goodNum;
    private Integer collectNum;
    private Long userId;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;
    @TableField(
        fill = FieldFill.INSERT_UPDATE
    )
    private LocalDateTime updateTime;
    private Integer version;
    private Integer isDeleted;


}
