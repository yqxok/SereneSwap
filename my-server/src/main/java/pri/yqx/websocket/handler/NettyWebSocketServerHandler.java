//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.websocket.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pri.yqx.common.util.JwtUtil;
import pri.yqx.websocket.NettyUtil;
import pri.yqx.websocket.service.WebSocketService;
import pri.yqx.websocket.service.WsMsgDto;
import pri.yqx.websocket.service.WsMsgType;

@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger log = LoggerFactory.getLogger(NettyWebSocketServerHandler.class);
    private WebSocketService webSocketService;
    public static final ConcurrentHashMap<Long, Channel> onlineUser = new ConcurrentHashMap();

    public NettyWebSocketServerHandler() {
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.webSocketService = (WebSocketService)SpringUtil.getBean(WebSocketService.class);
    }

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        WsMsgDto wsMsgDto = new WsMsgDto();
        wsMsgDto.setType(WsMsgType.HEART_BEAT.getValue());
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(wsMsgDto)));
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.userOffLine(ctx.channel());
        super.handlerRemoved(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("异常发生，异常消息 ={}", cause);
        this.userOffLine(ctx.channel());
        ctx.channel().close();
    }

    private void userOffLine(Channel channel) {
        this.webSocketService.userOffline(channel);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("已经10s没收到消息了,关闭连接");
            this.userOffLine(ctx.channel());
            ctx.channel().close();
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            String token = (String)NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);

            try {
                Long userId = JwtUtil.verifyAndGetId(token);
                this.webSocketService.addUserOnline(userId, ctx.channel());
                log.info("websocket握手成功");
            } catch (Exception var5) {
                ctx.channel().close();
                log.info("token验证失败");
            }
        } else {
            log.info("{}", evt.getClass());
        }

    }
}
