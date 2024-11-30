//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.msg.dao.CommentMsgDao;
import pri.yqx.msg.dao.MsgRoomDao;
import pri.yqx.msg.domain.dto.CommentMsgDto;
import pri.yqx.msg.domain.entity.CommentMsg;
import pri.yqx.msg.domain.entity.MsgRoom;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.domain.vo.CmMsgRoomtVo;
import pri.yqx.msg.domain.vo.CommentMsgVo;
import pri.yqx.msg.mapper.CommentMsgMapper;
import pri.yqx.msg.service.CommentMsgService;
import pri.yqx.msg.service.MsgRoomService;
import pri.yqx.msg.service.adapter.CommentMsgAdapter;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.service.cache.UserCache;

@Service
@Transactional
public class CommentMsgServiceImpl extends ServiceImpl<CommentMsgMapper, CommentMsg> implements CommentMsgService {
    @Resource
    private CommentMsgDao commentMsgDao;
    @Resource
    private GoodCache goodCache;
    @Resource
    private UserCache userCache;
    @Resource
    private MsgRoomDao msgRoomDao;
    @Resource
    private MsgRoomService msgRoomService;


    public void saveCommmentMsg(CommentMsgDto cmDto) {
        this.commentMsgDao.save(this.build(cmDto));
        this.msgRoomService.noReadMsgAdd(cmDto.getReceiverId(), 1, MsgRoomType.COMMENT_ROOM, MsgRoomType.TOTAL_MSG_ROOM);

    }

    private CommentMsg build(CommentMsgDto cmDto) {
        return new CommentMsg().setCommentId(cmDto.getCommentId()).setGoodId(cmDto.getGoodId())
                .setSenderId(cmDto.getSenderId()).setReceiverId(cmDto.getReceiverId())
                .setType(cmDto.getType()).setContent(cmDto.getContent());
    }

    public CursorPageVo<CommentMsgVo> getCursorPage(Long userId, CursorReq cursorReq) {
        CursorPageVo<CommentMsg> cursorPage = this.commentMsgDao.getCursorPage(userId, cursorReq.getCursor(), cursorReq.getPageSize());
        List<Set<Long>> propertySetList = MyBeanUtils.getPropertySetList(cursorPage.getList(), CommentMsg::getGoodId, CommentMsg::getSenderId, CommentMsg::getCommentId);
        Map<Long, Good> goodMap = this.goodCache.getAllCache(propertySetList.get(0));
        Map<Long, User> userMap = this.userCache.getAllCache(propertySetList.get(1));
        return CommentMsgAdapter.buildCursorPage(cursorPage, goodMap, userMap);
    }

    public CmMsgRoomtVo getCmMsgRoom(Long userId) {
        CommentMsg commentMsg = this.commentMsgDao.latestMsg(userId);
        CmMsgRoomtVo cmMsgRoomtVo = new CmMsgRoomtVo();
        if (Objects.isNull(commentMsg)) {
            return cmMsgRoomtVo;
        } else {
            Integer noReadNum = this.msgRoomDao.getNoReadNum(userId, MsgRoomType.COMMENT_ROOM);
            return this.build(commentMsg, cmMsgRoomtVo, noReadNum);
        }
    }

    public CommentMsgVo getCommentMsgVo(Long commentMsgId) {
        CommentMsg msg = this.commentMsgDao.getByCommentId(commentMsgId);
        User user = this.userCache.getCache(msg.getReceiverId());
        Good good = this.goodCache.getCache(msg.getGoodId());
        return CommentMsgAdapter.buildCommentMsgVo(msg, user, good);
    }

    private CmMsgRoomtVo build(CommentMsg commentMsg, CmMsgRoomtVo cmMsgRoomtVo, Integer noReadNum) {
        cmMsgRoomtVo.setNoReadNum(noReadNum);
        cmMsgRoomtVo.setCreateTime(commentMsg.getCreateTime());
        cmMsgRoomtVo.setMsg("有新的互动消息");
        return cmMsgRoomtVo;
    }
}
