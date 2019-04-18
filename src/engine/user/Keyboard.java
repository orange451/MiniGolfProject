package engine.user;

import java.util.ArrayList;

public class Keyboard {
	private ArrayList<PressedKey> keysPressed = new ArrayList<PressedKey>();
	private PressedKey lastKeyPressed;
	
	public void pressKey(String key) {
		boolean found = false;
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			if (keysPressed.get(i).keyName.equals(key)) {
				found = true;
			}
		}
		if (!found) {
			PressedKey pkey = new PressedKey(key);
			keysPressed.add(pkey);
			lastKeyPressed = pkey;
		}
	}

	public void releaseKey(String key) {
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			if (keysPressed.get(i).keyName.equals(key)) {
				keysPressed.get(i).letGo();
			}
		}
	}
	
	public boolean isKeyHeldDown(String key) {
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			if (keysPressed.get(i).held && keysPressed.get(i).keyName.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isKeyPressed(String key) {
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			if (keysPressed.get(i).ticks == 1 && keysPressed.get(i).keyName.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isKeyReleased(String key) {
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			if (keysPressed.get(i).keyName.equals(key) && keysPressed.get(i).released) {
				return true;
			}
		}
		return false;
	}

	public void tick() {
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			keysPressed.get(i).held = true;
			keysPressed.get(i).ticks++;
			if (keysPressed.get(i).hasKeyboardReleased())
				keysPressed.get(i).released = true;
		}
	}
	
	public void endTick() {
		for (int i = keysPressed.size() - 1; i >= 0; i--) {
			if (keysPressed.get(i).released) {
				keysPressed.remove(i);
			}
		}
	}

	public PressedKey getLastKeyPressed() {
		return lastKeyPressed;
	}
}
