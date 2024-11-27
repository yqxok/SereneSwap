package pri.yqx.user.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.user.domain.entity.Collect;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.domain.vo.CollectVo;

public class CollectAdapter {
    public CollectAdapter() {
    }

    public static CursorPageVo<CollectVo> buildCollectVoCursorPage(CursorPageVo<Collect> page, Map<Long, Good> goodMap, Map<Long, User> userMap) {
        List<CollectVo> collect = page.getList().stream().map((i) -> {
            CollectVo collectVo = (new CollectVo()).setCollectId(i.getCollectId()).setCreateTime(i.getCreateTime());
            Good good = (Good)goodMap.get(i.getGoodId());
            if (!Objects.isNull(good)) {
                CollectVo.GoodInfo goodInfo = (CollectVo.GoodInfo)MyBeanUtils.copyProperties(good, new CollectVo.GoodInfo());
                goodInfo.setPicUrl((PicUrl)good.getPicUrls().get(0));
                collectVo.setGoodInfo(goodInfo);
            } else {
                collectVo.setGoodInfo(new CollectVo.GoodInfo());
            }

            collectVo.setUserInfo((GoodVo.UserInfo)MyBeanUtils.copyProperties(userMap.get(i.getUserId()), new GoodVo.UserInfo()));
            return collectVo;
        }).collect(Collectors.toList());
        return CursorUtil.newCursorPageVo(page.getCursor(), page.getIsEnd(), collect);
    }
}