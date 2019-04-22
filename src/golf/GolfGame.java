package golf;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Game;
import engine.GameUniverse;
import engine.physics.PhysicsObject;
import golf.hole.Hole;
import golf.hole.*;

public class GolfGame extends Game {
	public static GolfPlayer player;
	private static Hole currentHole;
	
	@Override
	public void initialize(GameUniverse universe) {
		Game.universe = universe;
		new OutsideAmbientLight();
		
		// Objects in the scene
		setHole(new Hole2());
	}
	
	public static void setHole(Hole hole) {
		currentHole = hole;
		Game.clear();
		Game.addObject(player = new GolfPlayer());
		Game.addObject(hole);
		Game.addObject(new PhysicsObject(hole.getHoleModel(), true) {});
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
		g.drawString("Hello World!", 32, 32);
		
		if ( player.paused ) {
			g.setColor(new Color(0.2f,0.2f,0.2f,0.5f));
			g.fillRect(0, 0, getUniverse().getCanvas().getWidth(), getUniverse().getCanvas().getHeight());
		}
	}
}
