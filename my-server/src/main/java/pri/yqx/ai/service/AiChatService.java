package pri.yqx.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.ai.domain.entity.AiChat;
import pri.yqx.ai.domain.req.AiChatReq;
import pri.yqx.ai.domain.vo.AiChatVo;

import java.util.List;

public interface AiChatService extends IService<AiChat> {
    void saveChat(Long userId, AiChatReq aiChatReq);


    List<AiChat> getAiChats(Long roomId);
}