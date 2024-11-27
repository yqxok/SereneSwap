package pri.yqx.msg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.msg.domain.dto.CommentMsgDto;
import pri.yqx.msg.domain.entity.CommentMsg;
import pri.yqx.msg.domain.vo.CmMsgRoomtVo;
import pri.yqx.msg.domain.vo.CommentMsgVo;

public interface CommentMsgService extends IService<CommentMsg> {
    void saveCommmentMsg(CommentMsgDto cmDto);

    CursorPageVo<CommentMsgVo> getCursorPage(Long userId, CursorReq cursorReq);

    CmMsgRoomtVo getCmMsgRoom(Long userId);

    CommentMsgVo getCommentMsgVo(Long commentMsgId);
}