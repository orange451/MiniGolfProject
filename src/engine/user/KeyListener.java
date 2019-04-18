package engine.user;

import java.awt.event.KeyEvent;

import engine.Game;


public class KeyListener implements java.awt.event.KeyListener {	
	@Override
	public void keyPressed(KeyEvent arg0) {
		String key = getKeyFromKeyCode(arg0);
		System.out.println("Pressed key: " + key);
		Game.keyboard.pressKey(key);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		String key = getKeyFromKeyCode(arg0);
		Game.keyboard.releaseKey(key);
	}

	
	public String getKeyFromKeyCode(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String key = KeyEvent.getKeyText(keyCode);
		boolean holdShift = Game.keyboard.isKeyHeldDown("Shift");
		
		if (!holdShift) {
			if (keyCode == 47) {
				key = "/";
			}
			if (keyCode == 92) {
				key = "\'";
			}
			if (keyCode == 91) {
				key = "[";
			}
			if (keyCode == 93) {
				key = "]";
			}
		}else{
			if (keyCode == 44)
				key = "<";
			if (keyCode == 46)
				key = ">";
			if (keyCode == 222)
				key = "\"";
			if (keyCode == 59) {
				key = ":";
			}
			if (keyCode == 47) {
				key = "?";
			}
			if (keyCode == 92) {
				key = "|";
			}
			if (keyCode == 91) {
				key = "{";
			}
			if (keyCode == 93) {
				key = "}";
			}
		}
		if (keyCode == 37) {
			key = "Left";
		}
		if (keyCode == 39) {
			key = "Right";
		}
		if (keyCode == 40) {
			key = "Down";
		}
		if (keyCode == 38) {
			key = "Up";
		}
		if (keyCode == 35) {
			key = "End";
		}
		if (keyCode == 34) {
			key = "PageDown";
		}
		if (keyCode == 33) {
			key = "PageUp";
		}
		if (keyCode == 36) {
			key = "Home";
		}
		if (keyCode == 127) {
			key = "Delete";
		}
		if (keyCode == 155) {
			key = "Insert";
		}
		if (keyCode == 123) {
			key = "F12";
		}
		if (keyCode == 122) {
			key = "F11";
		}
		if (keyCode == 121) {
			key = "F10";
		}
		if (keyCode == 120) {
			key = "F9";
		}
		if (keyCode == 119) {
			key = "F8";
		}
		if (keyCode == 118) {
			key = "F7";
		}
		if (keyCode == 117) {
			key = "F6";
		}
		if (keyCode == 116) {
			key = "F5";
		}
		if (keyCode == 115) {
			key = "F4";
		}
		if (keyCode == 114) {
			key = "F3";
		}
		if (keyCode == 113) {
			key = "F2";
		}
		if (keyCode == 112) {
			key = "F1";
		}
		if (keyCode == 27) {
			key = "Escape";
		}
		if (keyCode == 192) {
			key = "Tild";
		}
		if (keyCode == 20) {
			key = "CapsLock";
		}
		if (keyCode == 16) {
			key = "Shift";
		}
		if (keyCode == 17) {
			key = "Control";
		}
		if (keyCode == 18) {
			key = "Alt";
		}
		if (keyCode == 32) {
			key = "Space";
		}
		if (keyCode == 45) {
			key = "Minus";
		}
		if (keyCode == 61) {
			key = "Equals";
		}
		if (keyCode == 8) {
			key = "Backspace";
		}
		if (keyCode == 92) {
			key = "Frontslash";
		}
		if (keyCode == 47) {
			key = "Backslash";
		}
		if (keyCode == 91) {
			key = "LeftBrace";
		}
		if (keyCode == 93) {
			key = "RightBrace";
		}
		if (keyCode == 10) {
			key = "Enter";
		}

		return key;
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
