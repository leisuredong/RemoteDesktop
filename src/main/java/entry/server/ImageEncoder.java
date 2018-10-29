package entry.server;

import entry.share.CaptureImage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ImageEncoder extends MessageToByteEncoder<CaptureImage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, CaptureImage msg, ByteBuf out) throws Exception {
		out.writeInt(msg.getLength());
		out.writeBytes(msg.getContent());
	}
}
