
package pri.yqx.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.comment.domain.dto.req.CmCursorReq;
import pri.yqx.comment.domain.dto.req.CommentReq;
import pri.yqx.comment.domain.dto.req.SonCmCurosrReq;
import pri.yqx.comment.domain.dto.req.SonCommentReq;
import pri.yqx.comment.domain.entity.Comment;
import pri.yqx.comment.domain.vo.CmCursorPageVo;
import pri.yqx.comment.domain.vo.CommentSonVo;
import pri.yqx.comment.domain.vo.CommentVo;
import pri.yqx.common.domain.response.CursorPageVo;

public interface CommentService extends IService<Comment> {
    CursorPageVo<CommentVo> getCursorPage(CmCursorReq cGet);

    Long saveComment(Long userId, CommentReq goodCommentDto);

    CursorPageVo<CommentVo> cursorPageWithGoodJob(Long userId, CmCursorReq cGet);

    CmCursorPageVo<CommentSonVo> getSonCursorPage(SonCmCurosrReq sonCmGetDto);

    CmCursorPageVo<CommentSonVo> getSonCursorPageWithGoodJob(Long userId, SonCmCurosrReq sonCmGetDto);

    void plusGoodJob(Long commentId, int num);

    void substractGoodJob(Long commentId, int num);

    CommentVo getCommentVo(Long commentId);

    Long saveSonComment(Long userId, SonCommentReq sonCommentDto);

    CommentSonVo getSonCommentVo(Long sonCommentId);
}
