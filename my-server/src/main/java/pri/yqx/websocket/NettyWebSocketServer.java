package pri.yqx.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pri.yqx.websocket.handler.HttpHeadersHandler;
import pri.yqx.websocket.handler.NettyWebSocketServerHandler;

@Component
public class NettyWebSocketServer {
    public static final NettyWebSocketServerHandler NETTY_WEB_SOCKET_SERVER_HANDLER = new NettyWebSocketServerHandler();
    @Value("${netty.port}")
    private int WEB_SOCKET_PORT;

    @PostConstruct
    public void init() throws InterruptedException {
        this.run();
    }

    private void run() throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(NettyRuntime.availableProcessors());
        ServerBootstrap serverBootstrap = ((ServerBootstrap)(new ServerBootstrap()).group(boss, worker).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new IdleStateHandler(10, 0, 0))
                        .addLast(new HttpServerCodec())
                        .addLast(new HttpObjectAggregator(8192))
                        .addLast(new HttpHeadersHandler())
                        .addLast(new WebSocketServerProtocolHandler("/netty"))
                        .addLast(NettyWebSocketServer.NETTY_WEB_SOCKET_SERVER_HANDLER);
            }
        });
        serverBootstrap.bind(7002).sync();
    }
}