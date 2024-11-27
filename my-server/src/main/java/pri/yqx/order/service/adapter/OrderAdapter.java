package pri.yqx.order.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.order.domain.entity.OrderUnion;
import pri.yqx.order.domain.vo.OrderDetailVo;
import pri.yqx.order.domain.vo.OrderVo;
import pri.yqx.user.domain.entity.User;

public class OrderAdapter {
    public OrderAdapter() {
    }

    public static CursorPageVo<OrderVo> buildOrderVoCursorPage(CursorPageVo<OrderUnion> cursorPageVo, Map<Long, User> userMap, Map<Long, Good> goodMap) {
        List<OrderVo> collect = cursorPageVo.getList().stream().map((i) -> {
            User user = userMap.get(i.getDealUserId());
            Good good = goodMap.get(i.getGoodId());
            OrderVo orderVo = MyBeanUtils.copyProperties(i, new OrderVo());
            return orderVo.setDealUser(MyBeanUtils.copyProperties(user, new OrderVo.UserInfo())).setGoodInfo(((OrderVo.GoodInfo)MyBeanUtils.copyProperties(good, new OrderVo.GoodInfo())).setPicUrl(good.getPicUrls().get(0)));
        }).collect(Collectors.toList());
        return CursorUtil.copyCursorPage(cursorPageVo, collect);
    }

    public static OrderDetailVo buildOrderVo(OrderUnion orderUnion, User dealUser, Good good) {
        OrderDetailVo o = (OrderDetailVo)MyBeanUtils.copyProperties(orderUnion, new OrderDetailVo());
        o.setDealUser((OrderVo.UserInfo)MyBeanUtils.copyProperties(dealUser, new OrderVo.UserInfo()));
        o.setAddressInfo((new OrderDetailVo.AddressInfo()).setAddress(orderUnion.getAddress()).setReceiver(orderUnion.getReceiver()).setPhoneNumber(orderUnion.getPhoneNumber()));
        if (!Objects.isNull(good)) {
            o.setGoodInfo((new OrderVo.GoodInfo()).setGoodId(good.getGoodId()).setStatus(good.getStatus()).setHtml(good.getHtml().length() > 20 ? good.getHtml().substring(0, 20) : good.getHtml()).setPrice(good.getPrice()).setPicUrl(good.getPicUrls().get(0)));
        }

        return o;
    }
}