//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.enums.GoodStatusEnums;
import pri.yqx.good.service.GoodService;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.order.dao.OrderDao;
import pri.yqx.order.dao.OrderInfoDao;
import pri.yqx.order.domain.dto.OrderCursorReq;
import pri.yqx.order.domain.dto.OrderMsgDto;
import pri.yqx.order.domain.dto.OrderReq;
import pri.yqx.order.domain.dto.StatusReq;
import pri.yqx.order.domain.entity.Order;
import pri.yqx.order.domain.entity.OrderInfo;
import pri.yqx.order.domain.entity.OrderUnion;
import pri.yqx.order.domain.enums.OrderStatusEnum;
import pri.yqx.order.domain.vo.OrderDetailVo;
import pri.yqx.order.domain.vo.OrderVo;
import pri.yqx.order.service.OrderService;
import pri.yqx.order.service.adapter.OrderAdapter;
import pri.yqx.order.service.event.OrderEvent;
import pri.yqx.user.dao.AddressDao;
import pri.yqx.user.dao.DormitoryDao;
import pri.yqx.user.domain.entity.Address;
import pri.yqx.user.domain.entity.Dormitory;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.service.cache.UserCache;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderInfoDao orderInfoDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserCache userCache;
    @Resource
    private GoodCache goodCache;
    @Resource
    private DormitoryDao dormitoryDao;
    @Resource
    private AddressDao addressDao;
    @Resource
    private GoodService goodService;
    @Resource
    private ApplicationContext applicationContext;

    public OrderServiceImpl() {
    }

    public CursorPageVo<OrderVo> getCursorPage(Long userId, OrderCursorReq cursorDto) {
        CursorPageVo<OrderUnion> cursorPage = this.orderDao.getCursorPage(userId, cursorDto);
        List<Set<Long>> relateId = MyBeanUtils.getPropertySetList(cursorPage.getList(), OrderUnion::getGoodId, OrderUnion::getDealUserId);
        Map<Long, Good> goodMap = this.goodCache.getAllCache(relateId.get(0));
        Map<Long, User> userMap = this.userCache.getAllCache(relateId.get(1));
        return OrderAdapter.buildOrderVoCursorPage(cursorPage, userMap, goodMap);
    }

    public OrderDetailVo getOrderDetail(Long userId, Long orderId) {
        OrderUnion orderUnion = this.orderDao.getOrderUnion(userId, orderId);
        AssertUtil.isEmpty(orderUnion, "orderId无效");
        User dealUser = this.userCache.getCache(orderUnion.getDealUserId());
        Good good = this.goodCache.getCache(orderUnion.getGoodId());
        return OrderAdapter.buildOrderVo(orderUnion, dealUser, good);
    }

    @Transactional
    public void updateStatus(Long userId, StatusReq statusReq) {
        OrderUnion order = this.orderDao.getOrderUnion(userId, statusReq.getOrderId());
        AssertUtil.isEmpty(order, "orderId无效");
        AssertUtil.isTrue(!order.getIsBuyer(), "没有权限修改订单状态");
        AssertUtil.isTrue(!Objects.equals(order.getStatus(), OrderStatusEnum.DEALING.getStatus()), "当前订单状态不可改变");
        if (Objects.equals(statusReq.getStatus(), OrderStatusEnum.CANCELDEAL.getStatus())) {
            Good good = this.goodCache.getCache(order.getGoodId());
            this.goodService.updateGoodById((new Good()).setGoodId(good.getGoodId()).setStatus(GoodStatusEnums.UNSELL.getStatus()).setGoodNum(good.getGoodNum() + order.getBuyNum()));
        }

        Order order1 = (new Order()).setOrderId(order.getOrderId()).setStatus(statusReq.getStatus()).setDealTime(Objects.equals(statusReq.getStatus(), OrderStatusEnum.DEALED.getStatus()) ? LocalDateTime.now() : null);
        this.orderDao.updateById(order1);
        this.applicationContext.publishEvent(new OrderEvent(this, new OrderMsgDto(order.getOrderId(), statusReq.getStatus(), order.getDealUserId())));
    }

    @Transactional
    public Long saveOrder(Long userId, OrderReq orderReq) {
        Good good = this.goodCache.getCache(orderReq.getGoodId());
        AssertUtil.isEmpty(good, "商品不存在");
        AssertUtil.isTrue(Objects.equals(good.getUserId(), userId), "本人不能购买自己的商品");
        AssertUtil.isTrue(orderReq.getBuyNum() > good.getGoodNum(), "商品存量不足");
        Address address = this.addressDao.getAddress(userId, orderReq.getAddressId());
        AssertUtil.isEmpty(address, "地址不存在");
        long orderId = IdWorker.getId();
        Order order = this.buildOrder(orderId, good, orderReq, address);
        this.orderDao.save(order);
        this.orderInfoDao.saveBatch(this.buildOrderInfos(order, userId, good));
        GoodStatusEnums status = Objects.equals(orderReq.getBuyNum(), good.getGoodNum()) ? GoodStatusEnums.SELLED : GoodStatusEnums.UNSELL;
        this.goodService.updateGoodById((new Good()).setGoodId(good.getGoodId()).setStatus(status.getStatus()).setGoodNum(good.getGoodNum() - orderReq.getBuyNum()));
        this.applicationContext.publishEvent(new OrderEvent(this, new OrderMsgDto(order.getOrderId(), OrderStatusEnum.DEALING.getStatus(), good.getUserId())));
        return orderId;
    }

    public OrderUnion getOrder(Long userId, Long orderId) {
        return this.orderDao.getOrderUnion(userId, orderId);
    }

    private List<OrderInfo> buildOrderInfos(Order order, Long userId, Good good) {
        OrderInfo orderInfo = (new OrderInfo()).setOrderId(order.getOrderId()).setUserId(userId).setDealUserId(good.getUserId()).setIsBuyer(true);
        OrderInfo orderInfo1 = (new OrderInfo()).setOrderId(order.getOrderId()).setUserId(good.getUserId()).setDealUserId(userId).setIsBuyer(false);
        return Arrays.asList(orderInfo, orderInfo1);
    }

    private Order buildOrder(Long orderId, Good good, OrderReq orderReq, Address address) {
        return (new Order()).setOrderId(orderId).setAddress(this.buildAddress(address))
                .setBuyNum(orderReq.getBuyNum()).setStatus(0).setPhoneNumber(address.getPhoneNumber())
                .setReceiver(address.getReceiver()).setTotalPrice(good.getPrice().multiply(new BigDecimal(orderReq.getBuyNum())))
                .setGoodId(good.getGoodId());
    }

    private String buildAddress(Address address) {
        StringBuilder builder = new StringBuilder();
        Dormitory dormitory = (Dormitory)this.dormitoryDao.getById(address.getDormitoryId());
        builder.append(dormitory.getSchool()).append(" ").append(dormitory.getZone()).append(" ").append(dormitory.getDormiName()).append(" ").append(address.getDormiNum());
        return builder.toString();
    }
}
