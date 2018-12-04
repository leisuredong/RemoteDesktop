package entry.client;

import entry.share.CaptureImage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// accept msg from ImageDecoder
		CaptureImage captureImage = (CaptureImage) msg;
		RemoteDesktop.panel.display(captureImage.getContent());
		String message = String.format("%20s", "ACK");// send ACK after painting an image
		ctx.writeAndFlush(message + '\n');
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println("An existing connection was forcibly closed by the remote host");
	}
}
