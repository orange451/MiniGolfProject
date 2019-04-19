package engine.io;

import java.awt.event.MouseEvent;
import engine.Game;

public class MouseListener implements java.awt.event.MouseListener {

	@Override
	public void mousePressed(MouseEvent e) {
		Game.mouse.pressMouse(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Game.mouse.releaseMouse(e.getButton());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
