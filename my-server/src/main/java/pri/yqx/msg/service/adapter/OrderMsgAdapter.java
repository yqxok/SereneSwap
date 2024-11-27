package pri.yqx.msg.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.msg.domain.entity.OrderMsg;
import pri.yqx.msg.domain.vo.OrderMsgVo;
import pri.yqx.order.domain.enums.OrderStatusEnum;
import pri.yqx.user.domain.entity.User;

public class OrderMsgAdapter {
    public OrderMsgAdapter() {
    }

    public static CursorPageVo<OrderMsgVo> buildCursorPage(CursorPageVo<OrderMsg> cursorPage, Map<Long, Good> goodMap, Map<Long, User> userMap) {
        List<OrderMsgVo> collect = cursorPage.getList().stream().map((i) -> {
            OrderMsgVo orderMsgVo = (new OrderMsgVo()).setOrderId(i.getOrderId()).setOrderMsgId(i.getOrderMsgId()).setCreateTime(i.getCreateTime()).setStatus(OrderStatusEnum.getMsgWithStatus(i.getStatus()));
            Good good = goodMap.get(i.getGoodId());
            User user = userMap.get(i.getSenderId());
            orderMsgVo.setGoodInfo((new OrderMsgVo.GoodInfo()).setGoodId(good.getGoodId()).setHtml(good.getHtml().length() > 20 ? good.getHtml().substring(0, 20) : good.getHtml()).setPicUrl((PicUrl)good.getPicUrls().get(0)));
            return orderMsgVo.setUserInfo(MyBeanUtils.copyProperties(user, new OrderMsgVo.UserInfo()));
        }).collect(Collectors.toList());
        return CursorUtil.copyCursorPage(cursorPage, collect);
    }

    public static OrderMsgVo buildOrderMsgVo(OrderMsg orderMsg, User user, Good good) {
        OrderMsgVo orderMsgVo = (new OrderMsgVo()).setOrderId(orderMsg.getOrderId()).setOrderMsgId(orderMsg.getOrderMsgId()).setCreateTime(orderMsg.getCreateTime()).setStatus(OrderStatusEnum.getMsgWithStatus(orderMsg.getStatus()));
        orderMsgVo.setGoodInfo((new OrderMsgVo.GoodInfo()).setGoodId(good.getGoodId()).setHtml(good.getHtml().length() > 20 ? good.getHtml().substring(0, 20) : good.getHtml()).setPicUrl((PicUrl)good.getPicUrls().get(0)));
        return orderMsgVo.setUserInfo(MyBeanUtils.copyProperties(user, new OrderMsgVo.UserInfo()));
    }
}