//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.comment.dao.CommentDao;
import pri.yqx.comment.domain.dto.req.CmCursorReq;
import pri.yqx.comment.domain.dto.req.CommentReq;
import pri.yqx.comment.domain.dto.req.SonCmCurosrReq;
import pri.yqx.comment.domain.dto.req.SonCommentReq;
import pri.yqx.comment.domain.vo.CmCursorPageVo;
import pri.yqx.comment.domain.vo.CommentSonVo;
import pri.yqx.comment.domain.vo.CommentVo;
import pri.yqx.comment.service.CommentService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.sensitive.SensitiveWordConverter;

@RestController
@RequestMapping({"/comment"})
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private SensitiveWordConverter sensitiveWordConverter;
    /**
     * 发布评论
     * @param goodCommentReq
     * @return
     */
    @PostMapping
    public Result<CommentVo> saveComment(@Validated @RequestBody CommentReq goodCommentReq) {
        //敏感词转换
        goodCommentReq.setContent(sensitiveWordConverter.convertStr(goodCommentReq.getContent()));
        Long commentId = this.commentService.saveComment(ThreadHolder.get(), goodCommentReq);
        return Result.success(this.commentService.getCommentVo(commentId), "评论发表成功");
    }

    /**
     * 发布回复评论
     * @param sonCommentReq
     * @return
     */
    @PostMapping({"/son"})
    public Result<String> saveSonComment(@Validated @RequestBody SonCommentReq sonCommentReq) {
        //敏感词转换
        sonCommentReq.setContent(sensitiveWordConverter.convertStr(sonCommentReq.getContent()));
        this.commentService.saveSonComment(ThreadHolder.get(), sonCommentReq);
        return Result.success(null, "回复评论发表成功");
    }

    /**
     * 不带登录状态的评论
     * @param cGet
     * @return
     */
    @PostMapping({"/no/records"})
    public Result<CursorPageVo<CommentVo>> cursorPage(@RequestBody CmCursorReq cGet) {
        CursorPageVo<CommentVo> cursorPage = this.commentService.getCursorPage(cGet);
        return Result.success(cursorPage, "查询所有评论成功");
    }

    /**
     * 带登录状态的评论
     * @param cGet
     * @return
     */
    @PostMapping({"/records"})
    public Result<CursorPageVo<CommentVo>> cursorPageWithGoodJob(@RequestBody CmCursorReq cGet) {
        CursorPageVo<CommentVo> cursorPage = this.commentService.cursorPageWithGoodJob(ThreadHolder.get(), cGet);
        return Result.success(cursorPage, "查询所有评论成功");
    }

    /**
     * 不带登录状态的子评论
     * @param sonCmGetDto
     * @return
     */
    @PostMapping({"/no/sonRecords"})
    public Result<CmCursorPageVo<CommentSonVo>> sonCursorPage(@RequestBody SonCmCurosrReq sonCmGetDto) {
        CmCursorPageVo<CommentSonVo> cursorPage = this.commentService.getSonCursorPage(sonCmGetDto);
        return Result.success(cursorPage, "查询所有评论成功");
    }

    /**
     * 带登录状态的子评论
     * @param sonCmGetDto
     * @return
     */
    @PostMapping({"/sonRecords"})
    public Result<CmCursorPageVo<CommentSonVo>> sonCursorPageWithGoodJob(@RequestBody SonCmCurosrReq sonCmGetDto) {
        CmCursorPageVo<CommentSonVo> cursorPage = this.commentService.getSonCursorPageWithGoodJob(ThreadHolder.get(), sonCmGetDto);
        return Result.success(cursorPage, "查询所有评论成功");
    }
}
