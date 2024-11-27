//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.controller;

import javax.annotation.Resource;
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

@RestController
@RequestMapping({"/comment"})
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private CommentDao commentDao;

    public CommentController() {
    }

    @PostMapping
    public Result<CommentVo> saveComment(@Validated @RequestBody CommentReq goodCommentReq) {
        Long commentId = this.commentService.saveComment(ThreadHolder.get(), goodCommentReq);
        return Result.success(this.commentService.getCommentVo(commentId), "评论发表成功");
    }

    @PostMapping({"/son"})
    public Result<String> saveSonComment(@Validated @RequestBody SonCommentReq sonCommentDto) {
        this.commentService.saveSonComment(ThreadHolder.get(), sonCommentDto);
        return Result.success(null, "回复评论发表成功");
    }

    @PostMapping({"/no/records"})
    public Result<CursorPageVo<CommentVo>> cursorPage(@RequestBody CmCursorReq cGet) {
        CursorPageVo<CommentVo> cursorPage = this.commentService.getCursorPage(cGet);
        return Result.success(cursorPage, "查询所有评论成功");
    }

    @PostMapping({"/records"})
    public Result<CursorPageVo<CommentVo>> cursorPageWithGoodJob(@RequestBody CmCursorReq cGet) {
        CursorPageVo<CommentVo> cursorPage = this.commentService.cursorPageWithGoodJob(ThreadHolder.get(), cGet);
        return Result.success(cursorPage, "查询所有评论成功");
    }

    @PostMapping({"/no/sonRecords"})
    public Result<CmCursorPageVo<CommentSonVo>> sonCursorPage(@RequestBody SonCmCurosrReq sonCmGetDto) {
        CmCursorPageVo<CommentSonVo> cursorPage = this.commentService.getSonCursorPage(sonCmGetDto);
        return Result.success(cursorPage, "查询所有评论成功");
    }

    @PostMapping({"/sonRecords"})
    public Result<CmCursorPageVo<CommentSonVo>> sonCursorPageWithGoodJob(@RequestBody SonCmCurosrReq sonCmGetDto) {
        CmCursorPageVo<CommentSonVo> cursorPage = this.commentService.getSonCursorPageWithGoodJob(ThreadHolder.get(), sonCmGetDto);
        return Result.success(cursorPage, "查询所有评论成功");
    }
}
