package pri.yqx.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.comment.dao.GoodJobDao;
import pri.yqx.comment.domain.dto.req.GoodJobReq;
import pri.yqx.comment.domain.entity.GoodJob;
import pri.yqx.comment.mapper.GoodJobMapper;
import pri.yqx.comment.service.CommentService;
import pri.yqx.comment.service.GoodJobService;
import pri.yqx.common.util.AssertUtil;

@Service
public class GoodJobServiceImpl extends ServiceImpl<GoodJobMapper, GoodJob> implements GoodJobService {
    @Autowired
    private GoodJobDao goodJobDao;
    @Autowired
    private CommentService commentService;

    public GoodJobServiceImpl() {
    }

    @Transactional
    public void saveGoodJob(Long userId, GoodJobReq goodJobDto) {
        GoodJob goodJob = this.goodJobDao.getGoodJob(userId, goodJobDto.getCommentId());
        AssertUtil.isNotEmpty(goodJob, "已经点赞该评论,不能重复点赞");
        this.goodJobDao.save((new GoodJob()).setCommentId(goodJobDto.getCommentId()).setUserId(userId));
        this.commentService.plusGoodJob(goodJobDto.getCommentId(), 1);
    }

    @Transactional
    public void deleteGoodJob(Long userId, GoodJobReq goodJobDto) {
        GoodJob goodJob = this.goodJobDao.getGoodJob(userId, goodJobDto.getCommentId());
        AssertUtil.isEmpty(goodJob, "未点赞该评论");
        this.goodJobDao.removeById(goodJob.getGjId());
        this.commentService.substractGoodJob(goodJobDto.getCommentId(), 1);
    }
}