package pri.yqx.chat.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import pri.yqx.chat.domain.dto.req.ChatCursorReq;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.chat.mapper.ChatContentMapper;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;

@Component
public class ChatContentDao extends ServiceImpl<ChatContentMapper, ChatContent> {
    public ChatContentDao() {
    }

    public CursorPageVo<ChatContent> getCursorPage(Long rooKey, ChatCursorReq gDto, LocalDateTime earlistTime) {
        return CursorUtil.init(this, gDto.getPageSize(), (lambda) -> {
            lambda.orderByDesc(ChatContent::getChatId).le(gDto.getCursor() != 0L, ChatContent::getChatId, gDto.getCursor())
                    .gt(ChatContent::getCreateTime, earlistTime)
                    .eq(ChatContent::getRoomKey, rooKey);
        }, ChatContent::getChatId);
    }

    public LocalDateTime getLatestMsgTime(Long roomKey) {
        Page<ChatContent> page = this.lambdaQuery().eq(ChatContent::getRoomKey, roomKey).orderByDesc(ChatContent::getCreateTime).page(new Page<ChatContent>(1L, 1L));
        return page.getRecords().get(0).getCreateTime();
    }
}