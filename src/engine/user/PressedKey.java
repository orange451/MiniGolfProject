package engine.user;

public class PressedKey {
	public String keyName;
	public boolean held;
	public int ticks;
	public boolean released;
	private boolean keyboardReleased;
	
	public PressedKey(String keyName) {
		this.keyName = keyName;
		this.held = false;
	}
	
	public void letGo() {
		this.keyboardReleased = true;
	}

	public boolean hasKeyboardReleased() {
		return keyboardReleased;
	}
}
