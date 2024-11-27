//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.msg.dao.MsgRoomDao;
import pri.yqx.msg.dao.OrderMsgDao;
import pri.yqx.msg.domain.entity.OrderMsg;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.domain.vo.OrderMsgRoomVo;
import pri.yqx.msg.domain.vo.OrderMsgVo;
import pri.yqx.msg.mapper.OrderMsgMapper;
import pri.yqx.msg.service.OrderMsgService;
import pri.yqx.msg.service.adapter.OrderMsgAdapter;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.service.cache.UserCache;

@Service
@Transactional
public class OrderMsgServiceImpl extends ServiceImpl<OrderMsgMapper, OrderMsg> implements OrderMsgService {
    private static final Logger log = LoggerFactory.getLogger(OrderMsgServiceImpl.class);
    @Resource
    private OrderMsgDao orderMsgDao;
    @Resource
    private UserCache userCache;
    @Resource
    private GoodCache goodCache;
    @Resource
    private MsgRoomDao msgRoomDao;

    public OrderMsgServiceImpl() {
    }

    public CursorPageVo<OrderMsgVo> getCursorPage(Long userId, CursorReq cursorReq) {
        CursorPageVo<OrderMsg> cursorPage = this.orderMsgDao.getCursorPage(userId, cursorReq.getCursor(), cursorReq.getPageSize());
        List<Set<Long>> list = MyBeanUtils.getPropertySetList(cursorPage.getList(), OrderMsg::getGoodId, OrderMsg::getSenderId);
        Map<Long, Good> goodMap = this.goodCache.getAllCache(list.get(0));
        Map<Long, User> userMap = this.userCache.getAllCache(list.get(1));
        return OrderMsgAdapter.buildCursorPage(cursorPage, goodMap, userMap);
    }

    public OrderMsgRoomVo getOrderMsgRoomVo(Long userId) {
        OrderMsg orderMsg = this.orderMsgDao.latestMsg(userId);
        OrderMsgRoomVo orderMsgVo = new OrderMsgRoomVo();
        if (Objects.isNull(orderMsg)) {
            return orderMsgVo;
        } else {
            Integer noReadNum = this.msgRoomDao.getNoReadNum(userId, MsgRoomType.ORDER_ROOM);
            this.build(orderMsg, orderMsgVo, noReadNum);
            return orderMsgVo;
        }
    }

    public OrderMsgVo getOrderMsgVo(Long id) {
        OrderMsg orderMsg = (OrderMsg)this.orderMsgDao.getById(id);
        User user = this.userCache.getCache(orderMsg.getReceiverId());
        Good good = this.goodCache.getCache(orderMsg.getGoodId());
        return OrderMsgAdapter.buildOrderMsgVo(orderMsg, user, good);
    }

    private void build(OrderMsg orderMsg, OrderMsgRoomVo orderMsgVo, Integer noReadNum) {
        orderMsgVo.setCreateTime(orderMsg.getCreateTime());
        orderMsgVo.setNoReadNum(noReadNum);
        orderMsgVo.setContent("有新的订单消息了");
    }
}
