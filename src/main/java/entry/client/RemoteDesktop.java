package entry.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import io.netty.buffer.Unpooled;

public class RemoteDesktop {

	public static MyPanel panel;

	public static void main(String[] args) {

		try {
			Client client = new Client();
			// you can separate client and server, and replace "localhost" with real address
			if (client.connectServer("localhost")) {
				if (client != null)
					// send a message to wake server up to send capture
					client.channel.writeAndFlush(Unpooled.copiedBuffer("start".getBytes()));
			}

			// initial frame
			JFrame frame = new JFrame();
			panel = new MyPanel();
			frame.add(panel);
			frame.setSize(1600, 900);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class MyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	ByteArrayInputStream bais;

	// accept and handler byte array from ClientHandler
	public void display(byte[] imageByte) {
		bais = new ByteArrayInputStream(imageByte);
		// invoke paint()
		this.repaint();
	}

	// paint the capture on the panel
	public void paint(Graphics g) {
		try {
			image = ImageIO.read(bais);
			g.drawImage(image, 0, 0, this);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}