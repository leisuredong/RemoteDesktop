package entry.client;

public class KeyOperation implements IKeyOperation {
	private String message;
	private Client client;

	public KeyOperation() {
		message = new String();
		client = Client.getInstance();
	}

	@Override
	public void keyDown(int keycode) {
		String command = String.format("%20s", "keyDown");
		String keyCode = String.format("%10d", keycode);
		message = command + keyCode;
		client.channel.writeAndFlush(message + '\n');
	}

	@Override
	public void keyUp(int keycode) {
		String command = String.format("%20s", "keyUp");
		String keyCode = String.format("%10d", keycode);
		message = command + keyCode;
		client.channel.writeAndFlush(message + '\n');
	}

	@Override
	public void keyType(int keycode) {
		String command = String.format("%20s", "keyType");
		String keyCode = String.format("%10d", keycode);
		message = command + keyCode;
		client.channel.writeAndFlush(message + '\n');
	}

}
