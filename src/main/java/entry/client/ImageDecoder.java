package entry.client;

import java.util.List;

import entry.share.CaptureImage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class ImageDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int length = in.readInt();

		byte[] content = new byte[length];
		in.readBytes(content);

		CaptureImage captureImage = new CaptureImage();
		captureImage.setLength(length);
		captureImage.setContent(content);

		out.add(captureImage);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// cause.printStackTrace();
		System.err.println("An existing connection was forcibly closed by the remote host");
	}
}
