package golf;

import java.awt.Graphics2D;

import engine.Game;
import engine.GameUniverse;
import engine.obj.ObjModel;
import engine.physics.PhysicsObject;

public class GolfGame extends Game {
	public static GolfPlayer player;
	
	@Override
	public void initialize(GameUniverse universe) {
		Game.universe = universe;
		new OutsideAmbientLight();
		
		// Objects in the scene
		Game.addObject(player = new GolfPlayer());
		Game.addObject(new Floor());
		Game.addObject(new PhysicsObject(ObjModel.load("Resources/Models/testHole.obj"), true) {
			{
				this.getBody().setRestitution(0.6f);
			}
		});
	}
	
	@Override
	public void step(float deltaTime) {
		super.step(deltaTime);
	}

	@Override
	public void paint(Graphics2D g) {
		g.fillRect(32, 32, 64, 64);
	}
}
