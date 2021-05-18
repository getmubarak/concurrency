
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
 
/**
 * Handles a server-side channel.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
 
	// List of connected client channels.
	static final List<Channel> channels = new ArrayList<Channel>();
 
	/*
	 * Whenever client connects to server through channel, add his channel to the
	 * list of channels.
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		System.out.println("Client joined - " + ctx);
		channels.add(ctx.channel());
	}
 
	/*
	 * When a message is received from client, send that message to all channels.
	 * FOr the sake of simplicity, currently we will send received chat message to
	 * all clients instead of one specific client. This code has scope to improve to
	 * send message to specific client as per senders choice.
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("Server received - " + msg);
		for (Channel c : channels) {
			c.writeAndFlush("-> " + msg + '\n');
		}
	}
 
	/*
	 * In case of exception, close channel. One may chose to custom handle exception
	 * & have alternative logical flows.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println("Closing connection for client - " + ctx);
		ctx.close();
	}
} 
public final class ChatServer {
 
	// Port where chat server will listen for connections.
	static final int PORT = 8007;
 
	public static void main(String[] args) throws Exception {
 
		/*
		 * Configure the server.
		 */
 
		// Create boss & worker groups. Boss accepts connections from client. Worker
		// handles further communication through connections.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
 
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup) // Set boss & worker groups
					.channel(NioServerSocketChannel.class)// Use NIO to accept new connections.
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							/*
							 * Socket/channel communication happens in byte streams. String decoder &
							 * encoder helps conversion between bytes & String.
							 */
							p.addLast(new StringDecoder());
							p.addLast(new StringEncoder());
 
							// This is our custom server handler which will have logic for chat.
							p.addLast(new ChatServerHandler());
						}
					});
 
			// Start the server.
			ChannelFuture f = b.bind(PORT).sync();
			System.out.println("Chat Server started. Ready to accept chat clients.");
 
			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
