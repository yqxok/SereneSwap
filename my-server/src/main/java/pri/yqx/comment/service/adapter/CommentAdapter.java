//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.service.adapter;

import cn.hutool.core.collection.CollectionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import pri.yqx.comment.domain.entity.Comment;
import pri.yqx.comment.domain.entity.CommentSon;
import pri.yqx.comment.domain.entity.GoodJob;
import pri.yqx.comment.domain.vo.CmCursorPageVo;
import pri.yqx.comment.domain.vo.CommentSonVo;
import pri.yqx.comment.domain.vo.CommentVo;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.user.domain.entity.User;

public class CommentAdapter {
    public CommentAdapter() {
    }

    public static CursorPageVo<CommentVo> buildCommentCursorPage(CursorPageVo<Comment> cursorPage, Map<Long, CmCursorPageVo<CommentSon>> sonCommentMap, Map<Long, User> userMap, int sonCommentInitSize) {
        Map<Long, CmCursorPageVo<CommentSonVo>> sonVoMap = new HashMap<>();

        for(Map.Entry<Long, CmCursorPageVo<CommentSon>> entry:sonCommentMap.entrySet()){
            CmCursorPageVo<CommentSon> p1 = entry.getValue();
            List<CommentSonVo> collect = p1.getList().stream().map((i) -> {
                return (MyBeanUtils.copyProperties(i, new CommentSonVo())).setReplyName(((User)userMap.get(i.getReplyId())).getUserName()).setUserInfo((GoodVo.UserInfo)MyBeanUtils.copyProperties(userMap.get(i.getUserId()), new GoodVo.UserInfo()));
            }).collect(Collectors.toList());
            sonVoMap.put(entry.getKey(), new CmCursorPageVo<>(p1, collect));
        }


        CmCursorPageVo<CommentSonVo> emptyCursorPage = new CmCursorPageVo<>(0L, true, new ArrayList<>(), 0, true);
        List<CommentVo> collect =cursorPage.getList().stream().map((i) -> {
            CmCursorPageVo<CommentSonVo> sonComments = sonVoMap.get(i.getCommentId());
            return ((CommentVo)MyBeanUtils.copyProperties(i, new CommentVo())).setSonComments(Objects.isNull(sonComments) ? emptyCursorPage : sonComments).setUserInfo((CommentVo.UserInfo)MyBeanUtils.copyProperties(userMap.get(i.getUserId()), new CommentVo.UserInfo())).setInitSize(sonCommentInitSize).setPutAwayComment(!Objects.isNull(sonComments) && !sonComments.getIsEnd());
        }).collect(Collectors.toList());
        return CursorUtil.copyCursorPage(cursorPage, collect);
    }

    public static CursorPageVo<CommentVo> buildCommentCursorPage(CursorPageVo<CommentVo> cursorPage, Map<Long, GoodJob> goodJobMap) {
        List<CommentVo> list = cursorPage.getList();

        for (CommentVo c1 : list) {
            c1.setIsGoodJob(!Objects.isNull(goodJobMap.get(c1.getCommentId())));
            List<CommentSonVo> list1 = c1.getSonComments().getList();
            for (CommentSonVo c2 : list1) {
                c2.setIsGoodJob(!Objects.isNull(goodJobMap.get(c2.getSonCommentId())));
            }
        }

        return cursorPage;
    }

    public static Set<Long> getIdSet(CursorPageVo<Comment> cursorPage, Map<Long, CmCursorPageVo<CommentSon>> sonCommentMap) {
        HashSet<Long> ids = new HashSet<>();
        for (CmCursorPageVo<CommentSon> value : sonCommentMap.values()) {
            value.getList().forEach((i) -> {
                ids.add(i.getUserId());
                ids.add(i.getReplyId());
            });
        }


        cursorPage.getList().forEach((i) -> {
            ids.add(i.getUserId());
        });
        return ids;
    }

    public static Map<Long, CmCursorPageVo<CommentSon>> buildSonCMCursorPage(List<CommentSon> list, Integer size) {
        Map<Long, List<CommentSon>> map = new HashMap<>();
        for (CommentSon commentSon : list) {
            if (Objects.isNull(map.get(commentSon.getCommentId()))) {
                map.put(commentSon.getCommentId(), new ArrayList<>());
            }

            List<CommentSon> commentSons = map.get(commentSon.getCommentId());
            commentSons.add(commentSon);
        }


        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (i) -> {
            List<CommentSon> list1 = i.getValue();
            Boolean isEnd = list1.size() != size + 1;
            Long cursor = !list1.isEmpty() ? ((CommentSon)CollectionUtil.getLast(list1)).getSonCommentId() : null;
            if (list1.size() > size) {
                list1.remove(list1.size() - 1);
            }
            return new CmCursorPageVo<>(cursor, isEnd, list1, CollectionUtil.size(list1), isEnd);
        }));
    }

    public static Set<Long> getCommentIds(CursorPageVo<CommentVo> cursorPage) {
        List<CommentVo> list = cursorPage.getList();
        Set<Long> set = new HashSet<>();
        for (CommentVo c1 : list) {
            set.add(c1.getCommentId());
            List<CommentSonVo> list1 = c1.getSonComments().getList();
            for (CommentSonVo c2 : list1) {
                set.add(c2.getSonCommentId());
            }
        }
        return set;
    }

    public static CmCursorPageVo<CommentSonVo> buildSonCMVoCursorPage(CursorPageVo<CommentSon> sonCursorPage, Map<Long, User> userMap) {
        List<CommentSonVo> collect = sonCursorPage.getList().stream().map((i) ->
            MyBeanUtils.copyProperties(i, new CommentSonVo())
                    .setUserInfo(MyBeanUtils.copyProperties(userMap.get(i.getUserId()), new GoodVo.UserInfo())).setReplyName(((User)userMap.get(i.getReplyId())).getUserName())
        ).collect(Collectors.toList());
        return new CmCursorPageVo<>(sonCursorPage.getCursor(), sonCursorPage.getIsEnd(), collect, CollectionUtil.size(collect), sonCursorPage.getIsEnd());
    }
}
