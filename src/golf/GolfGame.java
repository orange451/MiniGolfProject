package golf;

import java.util.ArrayList;

import javax.vecmath.Point3f;

import engine.Game;
import engine.GameObject;
import engine.GameUniverse;
import engine.physics.PhysicsWorld;

public class GolfGame extends Game {
	public static GameUniverse universe;
	public static PhysicsWorld physicsWorld;
	public static Camera camera;
	public static Golfball ball;
	
	public ArrayList<GameObject> objects;
	
	@Override
	public void initialize(GameUniverse universe) {
		GolfGame.universe = universe;
		new SunEnvironment();
		
		// Physics
		physicsWorld = new PhysicsWorld();
		
		// Setup the viewing camera
		camera = new Camera();
		
		// Objects in the scene
		objects = new ArrayList<GameObject>();
		objects.add(ball = new Golfball());
		objects.add(new Floor());
	}
	
	@Override
	public void step(float deltaTime) {
		// Step physics
		if ( physicsWorld == null )
			return;
		physicsWorld.step(deltaTime);
		
		// Update all the objects
		synchronized(objects) {
			for (int i = 0; i < objects.size(); i++) {
				objects.get(i).update(deltaTime);
			}
		}
		
		// Update the camera
		Point3f to = ball.getPosition();
		camera.setEye( to.x+14, to.y+14, to.z+14 );
		camera.setTo(to.x, to.y, to.z);
		camera.update();
	}
}
