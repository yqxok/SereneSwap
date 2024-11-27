
package pri.yqx.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMsgDto {
    private Long orderId;
    private Integer status;
    private Long receiverId;


}
