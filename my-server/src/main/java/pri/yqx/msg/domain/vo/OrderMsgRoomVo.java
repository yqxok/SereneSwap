//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderMsgRoomVo {
    private Integer noReadNum;
    private String content = "暂无订单消息";
    private LocalDateTime createTime;


}
