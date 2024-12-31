package pri.yqx.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pri.yqx.ai.dao.AiChatDao;
import pri.yqx.ai.domain.entity.AiChat;
import pri.yqx.ai.domain.entity.AiRoom;
import pri.yqx.ai.domain.enums.AiChatEnums;
import pri.yqx.ai.domain.req.AiChatReq;
import pri.yqx.ai.domain.vo.AiChatVo;
import pri.yqx.ai.mapper.AiChatMapper;
import pri.yqx.ai.service.AiChatService;
import pri.yqx.ai.service.AiRoomService;
import pri.yqx.common.constant.RedisKey;
import pri.yqx.common.util.AssertUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiChatServiceImpl extends AiChatDao implements AiChatService {
    @Autowired
    private AiRoomService aiRoomService;

    @Override
    public void saveChat(Long userId, AiChatReq aiChatReq) {
        AiRoom room = aiRoomService.getById(aiChatReq.getRoomId());
        AssertUtil.isEmpty(room,"房间不存在");

        AiChat aiChat = new AiChat(),userChat = new AiChat();
        userChat.setRoomId(room.getRoomId()).setContent(aiChatReq.getUserContent()).setType(AiChatEnums.USER.getType())
                .setCreateTime(LocalDateTime.now()).setGoods("[]");
        //ai消息设置延迟5秒
        aiChat.setRoomId(room.getRoomId()).setContent(aiChatReq.getAiContent()).setType(AiChatEnums.AI.getType()).setGoods(aiChatReq.getGoods())
                .setCreateTime(LocalDateTime.now().plusSeconds(10L));
        this.saveBatch(List.of(aiChat,userChat));
    }

    @Override
//    @Cacheable(value = RedisKey.Ai_ROOM,key = "#roomId")
    public List<AiChat> getAiChats(Long roomId) {

        return this.lambdaQuery().eq(AiChat::getRoomId, roomId).orderByDesc(AiChat::getCreateTime).list();
    }


}