package pri.yqx.msg.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.msg.domain.entity.CommentMsg;
import pri.yqx.msg.domain.enums.CommentMsgType;
import pri.yqx.msg.domain.vo.CommentMsgVo;
import pri.yqx.user.domain.entity.User;

public class CommentMsgAdapter {


    public static CursorPageVo<CommentMsgVo> buildCursorPage(CursorPageVo<CommentMsg> cursorPage, Map<Long, Good> goodMap, Map<Long, User> userMap) {
        List<CommentMsgVo> collect = cursorPage.getList().stream().map((i) -> {
            CommentMsgVo commentMsgVo = (new CommentMsgVo()).setCommentId(i.getCommentId()).setContent(i.getContent()).setCmMsgId(i.getCmMsgId()).setCreateTime(i.getCreateTime());
            commentMsgVo.setUserInfo((CommentMsgVo.UserInfo)MyBeanUtils.copyProperties(userMap.get(i.getSenderId()), new CommentMsgVo.UserInfo()));
            Good good = (Good)goodMap.get(i.getGoodId());
            commentMsgVo.setGoodInfo((new CommentMsgVo.GoodInfo()).setGoodId(good.getGoodId()).setPicUrl(good.getPicUrls().get(0)));
            if (Objects.equals(i.getType(), CommentMsgType.FATHER_COMMENT.getType())) {
                commentMsgVo.setType(CommentMsgType.FATHER_COMMENT.getMsg());
            } else {
                commentMsgVo.setType(CommentMsgType.SON_COMMENT.getMsg());
            }

            return commentMsgVo;
        }).collect(Collectors.toList());
        return CursorUtil.copyCursorPage(cursorPage, collect);
    }

    public static CommentMsgVo buildCommentMsgVo(CommentMsg msg, User user, Good good) {
        CommentMsgVo commentMsgVo = (new CommentMsgVo()).setCommentId(msg.getCommentId()).setContent(msg.getContent()).setCmMsgId(msg.getCmMsgId()).setCreateTime(msg.getCreateTime());
        commentMsgVo.setUserInfo(MyBeanUtils.copyProperties(user, new CommentMsgVo.UserInfo()));
        commentMsgVo.setGoodInfo((new CommentMsgVo.GoodInfo()).setGoodId(good.getGoodId()).setPicUrl(good.getPicUrls().get(0)));
        return commentMsgVo;
    }
}