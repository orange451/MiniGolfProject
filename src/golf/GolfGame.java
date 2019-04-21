package golf;

import engine.Game;
import engine.GameUniverse;
import engine.obj.ObjModel;
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
		Game.addObject(new PhysicsObject(ObjModel.load("Resources/Models/testHole.obj"), true) {
			{
				this.getBody().setRestitution(0.4f);
			}
		});
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
