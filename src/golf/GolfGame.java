package golf;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Game;
import engine.GameUniverse;
import golf.hole.*;

public class GolfGame extends Game {
	public static GolfPlayer player;
	private static Hole currentHole;
	public static int strokes;
	
	@Override
	public void initialize(GameUniverse universe) {
		Game.universe = universe;
		new OutsideAmbientLight();
		
		// Objects in the scene
		setHole(new Hole5());
	}
	
	public static void setHole(Hole hole) {
		currentHole = hole;
		strokes = 0;
		
		Game.clear();
		Game.addObject(player = new GolfPlayer());
		hole.create();
	}
	
	public static Hole getHole() {
		return currentHole;
	}
	
	@Override
	public void step(float deltaTime) {
		super.step(deltaTime);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("Position: ("+player.getBall().getPosition()+")", 16, 16);
		g.drawString("Strokes: " + strokes, 16, 32);
		
		if ( player.paused ) {
			g.setColor(new Color(0.2f,0.2f,0.2f,0.5f));
			g.fillRect(0, 0, getUniverse().getCanvas().getWidth(), getUniverse().getCanvas().getHeight());
		}
	}
}
