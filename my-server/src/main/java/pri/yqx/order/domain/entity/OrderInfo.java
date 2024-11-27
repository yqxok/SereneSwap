//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderInfo {
    @TableId
    private Long orderInfoId;
    private Long userId;
    private Long dealUserId;
    private Boolean isBuyer;
    private Long orderId;
    @TableLogic
    private Boolean isDeleted;


}
