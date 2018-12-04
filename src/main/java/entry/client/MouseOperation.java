package entry.client;

public class MouseOperation implements IMouseOperation {
	private String message;
	private boolean dragging;
	private Client client;

	public MouseOperation() {
		message = new String();
		dragging = false;
		client = Client.getInstance();
	}

	public int leftClick() {
		String command = String.format("%20s", "leftClick");
		message = command;
		client.channel.writeAndFlush(message + '\n');
		return 0;
	}

	public int leftDoubleClick() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int leftDown() {
		if (dragging == false) {
			dragging = true;
			String command = String.format("%20s", "leftDown");
			message = command;
			client.channel.writeAndFlush(message + '\n');
		}
		return 0;
	}

	public int leftUp() {
		dragging = false;
		String command = String.format("%20s", "leftUp");
		message = command;
		client.channel.writeAndFlush(message + '\n');
		return 0;
	}

	public int rightClick() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int rightDown() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int rightUp() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int middleClick() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int middleDown() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int middleUp() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int wheel(int rotation) {
		String command = String.format("%20s", "wheel");
		String Number = String.format("%10d", rotation);
		message = command + Number;
		client.channel.writeAndFlush(message + '\n');
		return 0;
	}

	public int moveTo(int x, int y) {
		String command = String.format("%20s", "moveTo");
		String X = String.format("%10d", x);
		String Y = String.format("%10d", y);
		message = command + X + Y;
		client.channel.writeAndFlush(message + '\n');
		return 0;
	}

}
