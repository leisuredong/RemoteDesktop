package entry.server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import entry.share.CaptureImage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		BufferedImage image;
		Robot robot = new Robot();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Rectangle rect = new Rectangle(0, 0, toolkit.getScreenSize().width, toolkit.getScreenSize().height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CaptureImage captureImage = new CaptureImage();
		while (true) {
			image = robot.createScreenCapture(rect);
			ImageIO.write(image, "jpg", baos);
			captureImage.setLength(baos.toByteArray().length);
			captureImage.setContent(baos.toByteArray());
			ctx.writeAndFlush(captureImage);
			Thread.sleep(100);
			baos.reset();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
