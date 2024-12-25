
package pri.yqx.good.service.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import lombok.*;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.dto.GoodCursorReq;
import pri.yqx.good.domain.dto.SelfGoodReq;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.entity.GoodCategory;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.good.domain.vo.GoodCollectVo;
import pri.yqx.good.domain.vo.GoodCursorPageVo;
import pri.yqx.good.domain.vo.GoodDetailVo;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.user.domain.entity.User;

public class GoodAdapter {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    static class TwoCollect{
       private List<GoodVo> list1;
       private List<GoodVo> list2;
       private Integer leftHeight;
       private Integer rightHeight;
    }

    private static TwoCollect sortList(List<GoodVo> goodVos,  GoodCursorReq goodCursorReq){
        goodVos.sort((i1, i2) ->i1.getPicUrl().getHeight()-i2.getPicUrl().getHeight());
        ArrayList<GoodVo> list1 = new ArrayList<>();
        ArrayList<GoodVo> list2 = new ArrayList<>();
        int height1=goodCursorReq.getLeftHeight(),height2=goodCursorReq.getRightHeight();
        for (int i = 0; i < goodVos.size(); i++) {
            int h=goodVos.get(i).getPicUrl().getHeight();
            int w=goodVos.get(i).getPicUrl().getWidth();
            h=goodCursorReq.getDeviceWidth()*h/w;
            //不能大于展示的最大高度
            if(h>goodCursorReq.getMaxHeight())
                h=goodCursorReq.getMaxHeight();
            //高度设置成前端适配高度
            goodVos.get(i).getPicUrl().setHeight(h);
            if(height1<height2){
                list1.add(goodVos.get(i));
                height1+=h;
            }else{
                list2.add(goodVos.get(i));
                height2+=h;
            }
        }
        return new TwoCollect(list1,list2,height1,height2);



    }
    public static GoodCursorPageVo buildGoodVoCursorPage(CursorPageVo<Good> goods, Map<Long, User> userMap, GoodCursorReq goodCursorReq) {
        List<GoodVo> collect = goods.getList().stream().map((i) -> {
            GoodVo goodVo = MyBeanUtils.copyProperties(i, new GoodVo());
            goodVo.setPicUrl(i.getPicUrls().get(0));
            GoodVo.UserInfo userInfo = MyBeanUtils.copyProperties(userMap.get(i.getUserId()), new GoodVo.UserInfo());
            goodVo.setUserInfo(userInfo);
            return goodVo;
        }).collect(Collectors.toList());

        //按高度排序
        TwoCollect twoCollect = sortList(collect, goodCursorReq);
        //赋值
        GoodCursorPageVo vo = new GoodCursorPageVo();
        vo.setLeftHeight(twoCollect.getLeftHeight());
        vo.setRightHeight(twoCollect.getRightHeight());
        vo.setList(twoCollect.getList1());
        vo.setAnotherList(twoCollect.getList2());
        vo.setCursor(goods.getCursor());
        vo.setSize(goods.getSize());
        vo.setIsEnd(goods.getIsEnd());
        return vo;
    }

    public static GoodDetailVo bildGoodDetailVo(Good good, User user, List<String> category) {
        GoodDetailVo goodDetailVo = MyBeanUtils.copyProperties(good, new GoodDetailVo());
        GoodVo.UserInfo userInfo = MyBeanUtils.copyProperties(user, new GoodVo.UserInfo());
        goodDetailVo.setUserInfo(userInfo);
        goodDetailVo.setCategories(category);
        return goodDetailVo;
    }

    public static CursorPageVo<Good> buildGoodCursorPage(CursorPageVo<GoodCategory> cursorPageVo, GoodCache goodCache) {
        Set<Long> goodIds = cursorPageVo.getList().stream().map(GoodCategory::getGoodId).collect(Collectors.toSet());
        Map<Long, Good> cacheMap = goodCache.getPhysicExitCaches(goodIds);
        List<Good> collect = cacheMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        return CursorUtil.newCursorPageVo(cursorPageVo.getCursor(), cursorPageVo.getIsEnd(), collect);
    }

    public static GoodCursorPageVo buildGoodVoList(List<Good> goods, User user, SelfGoodReq selfGoodReq) {
        List<GoodVo> collect = goods.stream().map((i) -> {
            GoodVo goodVo = MyBeanUtils.copyProperties(i, new GoodVo());
            goodVo.setPicUrl(i.getPicUrls().get(0));
            GoodVo.UserInfo userInfo = MyBeanUtils.copyProperties(user, new GoodVo.UserInfo());
            goodVo.setUserInfo(userInfo);
            return goodVo;
        }).collect(Collectors.toList());
        GoodCursorReq req = new GoodCursorReq().setDeviceWidth(selfGoodReq.getDeviceWidth()).setMaxHeight(selfGoodReq.getMaxHeight());

        TwoCollect twoCollect = sortList(collect, req);
        GoodCursorPageVo pageVo = new GoodCursorPageVo();
        pageVo.setList(twoCollect.getList1());
        pageVo.setAnotherList(twoCollect.getList2());
        return pageVo;
    }
}
