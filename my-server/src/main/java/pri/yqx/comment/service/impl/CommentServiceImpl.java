//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.comment.dao.CommentDao;
import pri.yqx.comment.dao.GoodJobDao;
import pri.yqx.comment.dao.SonCommentDao;
import pri.yqx.comment.domain.dto.req.CmCursorReq;
import pri.yqx.comment.domain.dto.req.CommentReq;
import pri.yqx.comment.domain.dto.req.SonCmCurosrReq;
import pri.yqx.comment.domain.dto.req.SonCommentReq;
import pri.yqx.comment.domain.entity.Comment;
import pri.yqx.comment.domain.entity.CommentSon;
import pri.yqx.comment.domain.entity.GoodJob;
import pri.yqx.comment.domain.vo.CmCursorPageVo;
import pri.yqx.comment.domain.vo.CommentSonVo;
import pri.yqx.comment.domain.vo.CommentVo;
import pri.yqx.comment.mapper.CommentMapper;
import pri.yqx.comment.service.CommentService;
import pri.yqx.comment.service.adapter.CommentAdapter;
import pri.yqx.comment.service.event.CommentEvent;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.service.cache.UserCache;

@Service
@Lazy
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private GoodJobDao goodJobDao;
    @Autowired
    private UserCache userCache;
    @Autowired
    private SonCommentDao sonCommentDao;
    @Autowired
    private GoodCache goodCache;
    @Autowired
    private ApplicationContext applicationContext;
    private static final int SON_COMMENT_INIT_SIZE = 1;

    public CommentServiceImpl() {
    }

    public CursorPageVo<CommentVo> getCursorPage(CmCursorReq cGet) {
        CursorPageVo<Comment> cursorPage = this.commentDao.getCursorPage(cGet.getGoodId(), cGet.getCursor(), cGet.getPageSize());
        if (CollectionUtil.isEmpty(cursorPage.getList())) {
            return new CursorPageVo();
        } else {
            Map<Long, CmCursorPageVo<CommentSon>> sonCommentMap = this.sonCommentDao.getSonCommentMap(MyBeanUtils.getPropertyList(cursorPage.getList(), Comment::getCommentId), 1);
            Set<Long> userIds = CommentAdapter.getIdSet(cursorPage, sonCommentMap);
            Map<Long, User> userMap = this.userCache.getAllCache(userIds);
            return CommentAdapter.buildCommentCursorPage(cursorPage, sonCommentMap, userMap, 1);
        }
    }

    public Long saveComment(Long userId, CommentReq cDto) {
        Good good = this.goodCache.getCache(cDto.getGoodId());
        AssertUtil.isEmpty(good, "goodId无效");
        long id = IdWorker.getId();
        this.commentDao.save(new Comment().setCommentId(id).setGoodId(cDto.getGoodId()).setUserId(userId).setContent(cDto.getContent()));
        this.applicationContext.publishEvent(new CommentEvent(this, id, 0));
        return id;
    }

    public CursorPageVo<CommentVo> cursorPageWithGoodJob(Long userId, CmCursorReq cGet) {
        CursorPageVo<CommentVo> cursorPage = this.getCursorPage(cGet);
        if (CollectionUtil.isEmpty(cursorPage.getList())) {
            return cursorPage;
        } else {
            Set<Long> ids = CommentAdapter.getCommentIds(cursorPage);
            if (ids.isEmpty()) {
                return cursorPage;
            } else {
                Map<Long, GoodJob> goodJobMap = this.goodJobDao.isGoodJob(userId, ids);
                return CommentAdapter.buildCommentCursorPage(cursorPage, goodJobMap);
            }
        }
    }

    public CmCursorPageVo<CommentSonVo> getSonCursorPage(SonCmCurosrReq sonCmGetDto) {
        CursorPageVo<CommentSon> sonCursorPage = this.sonCommentDao.getSonCursorPage(sonCmGetDto.getCommentId(), sonCmGetDto.getCursor(), sonCmGetDto.getPageSize());
        if (CollectionUtil.isEmpty(sonCursorPage.getList())) {
            return new CmCursorPageVo();
        } else {
            Set<Long> userIdSet = new HashSet();
            sonCursorPage.getList().forEach((i) -> {
                userIdSet.add(i.getUserId());
                userIdSet.add(i.getReplyId());
            });
            Map<Long, User> userMap = this.userCache.getAllCache(userIdSet);
            return CommentAdapter.buildSonCMVoCursorPage(sonCursorPage, userMap);
        }
    }

    public CmCursorPageVo<CommentSonVo> getSonCursorPageWithGoodJob(Long userId, SonCmCurosrReq sonCmGetDto) {
        CmCursorPageVo<CommentSonVo> sonCursorPage = this.getSonCursorPage(sonCmGetDto);
        if (CollectionUtil.isEmpty(sonCursorPage.getList())) {
            return new CmCursorPageVo();
        } else {
            Set<Long> sonCommentIds = MyBeanUtils.getPropertySet(sonCursorPage.getList(), CommentSonVo::getSonCommentId);
            Map<Long, GoodJob> goodJobs = this.goodJobDao.getGoodJobs(sonCommentIds, userId);
            sonCursorPage.getList().forEach((i) -> {
                i.setIsGoodJob(!Objects.isNull(goodJobs.get(i.getSonCommentId())));
            });
            return sonCursorPage;
        }
    }

    @Transactional
    public void plusGoodJob(Long commentId, int num) {
        Comment comment = (Comment)this.commentDao.getById(commentId);
        if (!Objects.isNull(comment)) {
            this.commentDao.plusGoodJobNum(commentId, num);
        } else {
            CommentSon commentSon = (CommentSon)this.sonCommentDao.getById(commentId);
            if (!Objects.isNull(commentSon)) {
                this.sonCommentDao.plusGoodJobNum(commentId, num);
            }

            AssertUtil.isTrue(Objects.isNull(comment) && Objects.isNull(commentSon), "该commentId无效");
        }
    }

    public void substractGoodJob(Long commentId, int num) {
        Comment comment = (Comment)this.commentDao.getById(commentId);
        if (!Objects.isNull(comment)) {
            this.commentDao.substractGoodJobNum(commentId, num);
        } else {
            CommentSon commentSon = (CommentSon)this.sonCommentDao.getById(commentId);
            if (!Objects.isNull(commentSon)) {
                this.sonCommentDao.substractGoodJobNum(commentId, num);
            }

        }
    }

    public CommentVo getCommentVo(Long commentId) {
        Comment comment = (Comment)this.commentDao.getById(commentId);
        AssertUtil.isEmpty(comment, "无效的commentId");
        User user = this.userCache.getCache(comment.getUserId());
        return this.build(comment, user);
    }

    public Long saveSonComment(Long userId, SonCommentReq scDto) {
        User user = this.userCache.getCache(scDto.getReplyId());
        AssertUtil.isEmpty(user, "replyUserId无效");
        Comment comment = (Comment)this.commentDao.getById(scDto.getFatherId());
        AssertUtil.isEmpty(comment, "fatherId无效");
        long id = IdWorker.getId();
        this.sonCommentDao.save((new CommentSon()).setSonCommentId(id).setCommentId(scDto.getFatherId()).setUserId(userId).setReplyId(scDto.getReplyId()).setContent(scDto.getContent()));
        this.applicationContext.publishEvent(new CommentEvent(this, id, 1));
        return id;
    }

    public CommentSonVo getSonCommentVo(Long sonCommentId) {
        CommentSon commentSon = (CommentSon)this.sonCommentDao.getById(sonCommentId);
        AssertUtil.isEmpty(commentSon, "无效的sonCommentId");
        Set<Long> ids = new HashSet(Arrays.asList(commentSon.getUserId(), commentSon.getReplyId()));
        Map<Long, User> userMap = this.userCache.getAllCache(ids);
        return this.buildCommentSonVo(commentSon, userMap);
    }

    private CommentSonVo buildCommentSonVo(CommentSon commentSon, Map<Long, User> userMap) {
        CommentSonVo vo = (CommentSonVo)MyBeanUtils.copyProperties(commentSon, new CommentSonVo());
        User user = (User)userMap.get(commentSon.getUserId());
        User replyUser = (User)userMap.get(commentSon.getReplyId());
        return vo.setReplyName(replyUser.getUserName()).setUserInfo((GoodVo.UserInfo)MyBeanUtils.copyProperties(user, new GoodVo.UserInfo()));
    }

    private CommentVo build(Comment comment, User user) {
        CommentVo commentVo = (CommentVo)MyBeanUtils.copyProperties(comment, new CommentVo());
        return commentVo.setUserInfo((CommentVo.UserInfo)MyBeanUtils.copyProperties(user, new CommentVo.UserInfo())).setSonComments(new CmCursorPageVo(0L, true, new ArrayList(), 0, true));
    }
}
