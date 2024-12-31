package pri.yqx.ai.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AiChatRoomVo {
    private Long roomId;
    private LocalDateTime createTime;
    private String roomName;
}
