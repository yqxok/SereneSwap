package pri.yqx.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pri.yqx.websocket.NettyUtil;

public class HttpHeadersHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(HttpHeadersHandler.class);

    public HttpHeadersHandler() {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest)msg;
            String token = (String)Optional.ofNullable(httpRequest.headers().get("token")).orElse("");
            NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, token);
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(msg);
        } else {
            ctx.fireChannelRead(msg);
        }

    }
}