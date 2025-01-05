
package pri.yqx.chat.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class ChatContentVo {
    private Long chatId;
    private Long SendUserId;
    private Long receiveUserId;
    private String content;
    private LocalDateTime createTime;

}
