
package pri.yqx.good.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.entity.GoodCategory;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.good.domain.vo.GoodDetailVo;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.user.domain.entity.User;

public class GoodAdapter {
    public GoodAdapter() {
    }

    public static CursorPageVo<GoodVo> buildGoodVoCursorPage(CursorPageVo<Good> goods, Map<Long, User> userMap) {
        List<GoodVo> collect = goods.getList().stream().map((i) -> {
            GoodVo goodVo = MyBeanUtils.copyProperties(i, new GoodVo());
            goodVo.setPicUrl(i.getPicUrls().get(0));
            GoodVo.UserInfo userInfo = MyBeanUtils.copyProperties(userMap.get(i.getUserId()), new GoodVo.UserInfo());
            goodVo.setUserInfo(userInfo);
            return goodVo;
        }).collect(Collectors.toList());
        return CursorUtil.newCursorPageVo(goods.getCursor(), goods.getIsEnd(), collect);
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

    public static List<GoodVo> buildGoodVoList(List<Good> goods, User user) {
        return goods.stream().map((i) -> {
            GoodVo goodVo = MyBeanUtils.copyProperties(i, new GoodVo());
            goodVo.setPicUrl(i.getPicUrls().get(0));
            GoodVo.UserInfo userInfo = MyBeanUtils.copyProperties(user, new GoodVo.UserInfo());
            goodVo.setUserInfo(userInfo);
            return goodVo;
        }).collect(Collectors.toList());
    }
}
