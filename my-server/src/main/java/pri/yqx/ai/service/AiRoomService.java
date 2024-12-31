package pri.yqx.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.ai.domain.entity.AiChat;
import pri.yqx.ai.domain.entity.AiRoom;
import pri.yqx.ai.domain.vo.AiChatRoomVo;

import java.util.List;

public interface AiRoomService extends IService<AiRoom> {
    Long createRoom(Long userId);

    List<AiChatRoomVo> getRooms(Long userId);
}