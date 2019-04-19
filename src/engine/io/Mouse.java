package engine.io;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;

import engine.Game;

public class Mouse {
	public static int LEFT_MOUSE = 1;
	public static int RIGHT_MOUSE = 3;
	public static int MIDDLE_MOUSE = 2;
	
	private ArrayList<MouseButton> mousebuttons = new ArrayList<MouseButton>();
	private int mouse_x;
	private int mouse_y;
	
	public void pressMouse(int mouseId) {
		boolean found = false;
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			if (mousebuttons.get(i).id == mouseId) {
				found = true;
			}
		}
		if (!found) {
			mousebuttons.add(new MouseButton(mouseId));
		}
	}

	public void releaseMouse(int mouseId) {
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			if (mousebuttons.get(i).id == mouseId) {
				mousebuttons.get(i).letGo();
			}
		}
	}
	
	public boolean isMouseButtonHeldDown(int mouseId) {
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			if (mousebuttons.get(i).held && mousebuttons.get(i).id == mouseId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMouseButtonPressed(int mouseId) {
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			if (mousebuttons.get(i).ticks == 1 && mousebuttons.get(i).id == mouseId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMouseButtonReleased(int mouseId) {
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			if (mousebuttons.get(i).id == mouseId && mousebuttons.get(i).released) {
				return true;
			}
		}
		return false;
	}
	
	public Point getMouseLocation() {
		return new Point(mouse_x, mouse_y);
	}

	public void tick() {
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			mousebuttons.get(i).held = true;
			mousebuttons.get(i).ticks++;
			if (mousebuttons.get(i).hasMouseReleased())
				mousebuttons.get(i).released = true;
		}
		
		try{
			Point location = MouseInfo.getPointerInfo().getLocation();
			Point FrameLocation = Game.universe.getCanvas().getLocationOnScreen();
			mouse_x = (int) location.getX() - (int) FrameLocation.getX();
			mouse_y = (int) location.getY() - (int) FrameLocation.getY();
		}catch(Exception e) {
			//
		}
	}
	
	public void endTick() {
		for (int i = mousebuttons.size() - 1; i >= 0; i--) {
			if (mousebuttons.get(i).released) {
				mousebuttons.remove(i);
			}
		}
	}
}
