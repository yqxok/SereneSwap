

package pri.yqx.chat.domain.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.vo.GoodVo;
@Data
@Accessors(chain = true)
public class RoomVo {
    private Long roomId;
    private Long goodId;
    private String latestMsg;
    private LocalDateTime createTime;
    private GoodVo.UserInfo userInfo;


}
