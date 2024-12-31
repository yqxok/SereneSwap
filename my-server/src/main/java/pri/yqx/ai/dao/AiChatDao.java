package pri.yqx.ai.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import pri.yqx.ai.domain.entity.AiChat;
import pri.yqx.ai.mapper.AiChatMapper;
import pri.yqx.ai.service.impl.AiChatServiceImpl;


public class AiChatDao  extends ServiceImpl<AiChatMapper, AiChat> {
}