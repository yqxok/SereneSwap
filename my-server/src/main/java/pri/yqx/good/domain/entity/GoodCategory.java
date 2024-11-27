//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class GoodCategory {
    @TableId
    private Long goodCategoryId;
    private Long goodId;
    private Integer level;
    private String categoryName;
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
    private Integer isDeleted;


}
