package entry.client;

public interface IMouseOperation {
	public int leftClick();

	public int leftDoubleClick();

	public int leftDown();

	public int leftUp();

	public int rightClick();

	public int rightDown();

	public int rightUp();

	public int middleClick();

	public int middleDown();

	public int middleUp();

	public int wheel(int rotation);

	public int moveTo(int x, int y);
}
