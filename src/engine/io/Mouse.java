package engine.io;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.Game;

public class Mouse {
	public static int LEFT_MOUSE = 1;
	public static int RIGHT_MOUSE = 3;
	public static int MIDDLE_MOUSE = 2;

	private ArrayList<MouseButton> mousebuttons = new ArrayList<MouseButton>();
	private int mouse_x;
	private int mouse_y;

	public static final Cursor BLANK;
	public static final Cursor NORMAL;
	
	static {
		BLANK = Toolkit.getDefaultToolkit().createCustomCursor( new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "BLANK");
		NORMAL = Cursor.getDefaultCursor();
	}
	
	public void setCursor( Cursor cursor ) {
		Game.getUniverse().getCanvas().setCursor(cursor);
	}

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

	private Robot robot;
	public void setMouseLocation(float x, float y) {
		if ( robot == null ) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
				return;
			}
		}
		Point FrameLocation = Game.getUniverse().getCanvas().getLocationOnScreen();
		robot.mouseMove((int)(FrameLocation.x+x), (int)(FrameLocation.y+y));
		mouse_x = (int)x;
		mouse_y = (int)y;
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
			Point FrameLocation = Game.getUniverse().getCanvas().getLocationOnScreen();
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
		lastMousePos.setLocation(getMouseLocation());
	}

	private Point lastMousePos = new Point();
	public Point getDelta() {
		Point mousePosition = getMouseLocation();
		Point delta = new Point(mousePosition.x-lastMousePos.x, mousePosition.y-lastMousePos.y);

		return delta;
	}
}
