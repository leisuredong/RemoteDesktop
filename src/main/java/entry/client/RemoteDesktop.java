package entry.client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RemoteDesktop {

	public static MyPanel panel;
	private RemoteMouseListener remoteMouseListener;
	private RemoteKeyListener remoteKeyListener;
	private static String ip;
	private static String resolution;

	public RemoteDesktop() {
		remoteMouseListener = new RemoteMouseListener();
		remoteKeyListener = new RemoteKeyListener();
		// initial frame
		JFrame frame = new JFrame();
		panel = new MyPanel();
		frame.add(panel);
		int x = Integer.parseInt(resolution.substring(0, resolution.indexOf('*')));
		int y = Integer.parseInt(resolution.substring(resolution.indexOf('*') + 1));
		frame.setSize(x, y);
		frame.setTitle("Remote Desktop");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel.addMouseListener(remoteMouseListener);
		panel.addMouseMotionListener(remoteMouseListener);
		panel.addMouseWheelListener(remoteMouseListener);
		panel.addKeyListener(remoteKeyListener);
	}

	public static void initSetUp() {
		final JFrame inputFrame = new JFrame();
		inputFrame.setLayout(new BorderLayout());
		JLabel ipLabel = new JLabel("IP address:");
		final JTextField ipTextField = new JTextField();
		JLabel resolutionLabel = new JLabel("resolution:");
		final JTextField resolutionTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ip = ipTextField.getText();
				resolution = resolutionTextField.getText();
				inputFrame.setVisible(false);
				try {
					Client client = Client.getInstance();
					if (client.connectServer(ip)) {
						if (client != null) {
							// send a message to wake server up to send capture
							String message = String.format("%20s", "ACK");
							client.channel.writeAndFlush(message + '\n');
						}
						new RemoteDesktop();
					}
				} catch (Exception e1) {
					 e1.printStackTrace();
				}
			}
		});
		JPanel inputPanel = new JPanel();
		inputPanel.add(ipLabel);
		inputPanel.add(ipTextField);
		inputPanel.add(resolutionLabel);
		inputPanel.add(resolutionTextField);
		inputPanel.setLayout(new GridLayout(2, 2, 0, 20));
		inputFrame.add(inputPanel, BorderLayout.NORTH);
		inputFrame.add(okButton, BorderLayout.SOUTH);
		inputFrame.setSize(300, 150);
		inputFrame.setTitle("SetUp");
		inputFrame.setVisible(true);
		inputFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		initSetUp();
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