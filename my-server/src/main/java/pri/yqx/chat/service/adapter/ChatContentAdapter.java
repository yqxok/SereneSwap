package pri.yqx.chat.service.adapter;

import java.util.List;
import java.util.stream.Collectors;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.chat.domain.vo.ChatContentVo;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;

public class ChatContentAdapter {
    public ChatContentAdapter() {
    }

    public static CursorPageVo<ChatContentVo> buildChatVoCursorPage(CursorPageVo<ChatContent> cursorPage) {
        List<ChatContent> list = cursorPage.getList();
        List<ChatContentVo> collect = list.stream().map((i) ->
                MyBeanUtils.copyProperties(i, new ChatContentVo())).collect(Collectors.toList());
        return CursorUtil.newCursorPageVo(cursorPage.getCursor(), cursorPage.getIsEnd(), collect);
    }
}