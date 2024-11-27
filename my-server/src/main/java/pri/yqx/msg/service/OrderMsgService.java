package pri.yqx.msg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.msg.domain.entity.OrderMsg;
import pri.yqx.msg.domain.vo.OrderMsgRoomVo;
import pri.yqx.msg.domain.vo.OrderMsgVo;

public interface OrderMsgService extends IService<OrderMsg> {
    CursorPageVo<OrderMsgVo> getCursorPage(Long userId, CursorReq cursorReq);

    OrderMsgRoomVo getOrderMsgRoomVo(Long userId);

    OrderMsgVo getOrderMsgVo(Long id);
}
