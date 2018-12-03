package entry.client;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class RemoteMouseListener extends MouseAdapter {

	private boolean dragging;
	private IMouseOperation mouseOperation;

	public RemoteMouseListener() {
		mouseOperation = new MouseOperation();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getModifiers()) {
		case InputEvent.BUTTON1_MASK:
			mouseOperation.leftClick();
			break;
		case InputEvent.BUTTON2_MASK:
			mouseOperation.middleClick();
			break;
		case InputEvent.BUTTON3_MASK:
			mouseOperation.rightClick();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (dragging)
			mouseOperation.leftUp();
		dragging = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dragging = true;
		if (e.getModifiers() == InputEvent.BUTTON1_MASK)
			mouseOperation.leftDown();
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = (int) e.getPoint().getX();
		int y = (int) e.getPoint().getY();
		mouseOperation.moveTo(x, y);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		mouseOperation.wheel(rotation);
	}
}
