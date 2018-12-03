package entry.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class Client {
	private Bootstrap bootstrap = new Bootstrap();
	protected Channel channel;
	private static Client client;

	public Client() {
		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
						pipeline.addLast(new ImageDecoder(), new ClientHandler());
					}
				});
	}

	public static Client getInstance() {
		if (client == null)
			client = new Client();
		return client;
	}

	public boolean connectServer(String address) throws Exception {
		try {
			if (address == null || address.equals(""))
				address = "localhost";
			channel = bootstrap.connect(address, 8095).sync().channel();
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("Can't connect to remote screen server...");
			return false;
		}
		return true;
	}
}
