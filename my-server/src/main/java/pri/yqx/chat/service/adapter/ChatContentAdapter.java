package pri.yqx.chat.service.adapter;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.chat.domain.vo.ChatContentVo;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.common.util.TimeConverterUtil;

public class ChatContentAdapter {


    public static CursorPageVo<ChatContentVo> buildChatVoCursorPage(CursorPageVo<ChatContent> cursorPage) {
        List<ChatContent> list = cursorPage.getList();
        List<ChatContentVo> collect = list.stream().map((i) ->
                MyBeanUtils.copyProperties(i, new ChatContentVo())).collect(Collectors.toList());
        //给sendTime属性赋值
//        TimeConverterUtil.convertTime(collect,"SendTime", ChatContentVo.class);

        return CursorUtil.newCursorPageVo(cursorPage.getCursor(), cursorPage.getIsEnd(), collect);
    }
}