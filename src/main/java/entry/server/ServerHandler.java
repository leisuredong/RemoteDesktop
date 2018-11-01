package entry.server;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import entry.share.CaptureImage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	private BufferedImage image;
	private Robot robot;
	private Toolkit toolkit;
	private Rectangle rect;
	private ByteArrayOutputStream baos;
	private CaptureImage captureImage;

	public ServerHandler() {
		try {
			robot = new Robot();
			toolkit = Toolkit.getDefaultToolkit();
			rect = new Rectangle(0, 0, toolkit.getScreenSize().width, toolkit.getScreenSize().height);
			baos = new ByteArrayOutputStream();
			captureImage = new CaptureImage();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		try {
			String message = new String(data, "utf-8");
			if (message.equals("ACK")) {
				image = robot.createScreenCapture(rect);
				ImageIO.write(image, "jpg", baos);
				captureImage.setLength(baos.toByteArray().length);
				captureImage.setContent(baos.toByteArray());
				ctx.writeAndFlush(captureImage);
				Thread.sleep(100);
				baos.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			buf.release();
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
