//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CmMsgRoomtVo {
    private Integer noReadNum;
    private String msg = "暂无互动消息";
    private LocalDateTime createTime;


}
