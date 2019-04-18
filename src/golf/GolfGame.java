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
	public static float direction;
	
	public ArrayList<GameObject> objects;
	
	@Override
	public void initialize(GameUniverse universe) {
		GolfGame.universe = universe;
		new OutsideAmbientLight();
		
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
		direction += deltaTime*0.3;
		float dist = 12;
		Point3f to = ball.getPosition();
		to.add(new Point3f(0,0.7f,0));
		camera.setEye( to.x+(float)Math.cos(direction)*dist, to.y+dist/5, to.z+(float)Math.sin(direction)*dist );
		camera.setTo(to.x, to.y, to.z);
		camera.update();
	}
}
