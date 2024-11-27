//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class Order {
    @TableId
    private Long orderId;
    private Long goodId;
    private String address;
    private String receiver;
    private String phoneNumber;
    private BigDecimal totalPrice;
    private Integer status;
    private Integer buyNum;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;
    private LocalDateTime dealTime;


}
