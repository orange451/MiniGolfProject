package golf;

import engine.Game;
import engine.GameUniverse;
import engine.physics.PhysicsObject;
import engine.physics.PhysicsWorld;

public class GolfGame extends Game {
	public static PhysicsWorld physicsWorld;
	public static GolfPlayer player;
	
	@Override
	public void initialize(GameUniverse universe) {
		Game.universe = universe;
		new OutsideAmbientLight();
		
		// Physics
		physicsWorld = new PhysicsWorld();
		
		// Objects in the scene
		Game.addObject(player = new GolfPlayer());
		Game.addObject(new Floor());
		Game.addObject(new Putter());
		Game.addObject(new PhysicsObject("Resources/Models/testHole.obj", true) {});
	}
	
	@Override
	public void step(float deltaTime) {
		// Step physics
		if ( physicsWorld == null )
			return;
		physicsWorld.step(deltaTime);
		
		// Update objects
		super.step(deltaTime);
	}
}
