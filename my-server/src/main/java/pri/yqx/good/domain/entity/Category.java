//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class Category implements Serializable {
    @TableId(type = IdType.NONE)
    private String categoryName;
    private String pkId;
    private Integer level;
    private Long createUser;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private Integer isDeleted;


}
