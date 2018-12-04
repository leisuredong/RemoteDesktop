package entry.server;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
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
		try {
			String message = (String) msg;
			switch (message.substring(0, 20).trim()) {
			case "ACK":
				image = robot.createScreenCapture(rect);
				ImageIO.write(image, "jpg", baos);
				captureImage.setLength(baos.toByteArray().length);
				captureImage.setContent(baos.toByteArray());
				ctx.writeAndFlush(captureImage);
				baos.reset();
				break;
			case "moveTo":
				int x = Integer.valueOf(message.substring(20, 30).trim());
				int y = Integer.valueOf(message.substring(30).trim());
				robot.mouseMove(x, y);
				break;
			case "leftClick":
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				break;
			case "rightClick":
				robot.mousePress(InputEvent.BUTTON3_MASK);
				robot.mouseRelease(InputEvent.BUTTON3_MASK);
				break;
			case "leftDown":
				robot.mousePress(InputEvent.BUTTON1_MASK);
				break;
			case "leftUp":
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				break;
			case "wheel":
				int wheelAmt = Integer.valueOf(message.substring(20, 30).trim());
				robot.mouseWheel(wheelAmt);
				break;
			case "keyType":
				int keycode = Integer.valueOf(message.substring(20, 30).trim());
				robot.keyPress(keycode);
				robot.keyRelease(keycode);
				break;
			case "keyDown":
				int keycode1 = Integer.valueOf(message.substring(20, 30).trim());
				robot.keyPress(keycode1);
				break;
			case "keyUp":
				int keycode2 = Integer.valueOf(message.substring(20, 30).trim());
				robot.keyRelease(keycode2);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// cause.printStackTrace();
		System.err.println("An existing connection was forcibly closed by the remote host");
	}
}
