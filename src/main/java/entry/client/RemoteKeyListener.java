package entry.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RemoteKeyListener extends KeyAdapter {

	private IKeyOperation keyOperation;

	public RemoteKeyListener() {
		keyOperation = new KeyOperation();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyOperation.keyDown(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyOperation.keyUp(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keyOperation.keyType(e.getKeyCode());
	}
}
