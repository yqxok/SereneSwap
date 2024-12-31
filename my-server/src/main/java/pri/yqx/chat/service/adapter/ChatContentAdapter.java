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
        TimeConverterUtil.convertTime(collect,"SendTime", ChatContentVo.class);

//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("MM月dd日 HH:mm");
//        collect.get(collect.size()-1).setSendTime("");
//        for (int i = 0; i < collect.size() - 1; i++) {
//            ChatContentVo current = collect.get(i);
//
//            ChatContentVo next = collect.get(i + 1);
//
//            LocalDateTime currentTime = current.getCreateTime();
//            LocalDateTime nextTime = next.getCreateTime();
//            long minutesDifference = Duration.between(nextTime, currentTime).toMinutes();
//
//            if (minutesDifference > 3) {
//                current.setSendTime("");
//            }
//        }
//        LocalDateTime now = LocalDateTime.now();
//
//        for (int i = 0; i < collect.size(); i++) {
//            ChatContentVo cur = collect.get(i);
//            if(cur.getSendTime()==null)
//                continue;
//            long daysDifference = Duration.between(cur.getCreateTime(), now).toDays();
//            if (daysDifference < 1) {
//                cur.setSendTime(cur.getCreateTime().format(timeFormatter));
//            } else {
//                cur.setSendTime(cur.getCreateTime().format(dayFormatter));
//            }
//
//        }
        return CursorUtil.newCursorPageVo(cursorPage.getCursor(), cursorPage.getIsEnd(), collect);
    }
}