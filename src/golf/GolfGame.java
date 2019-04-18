package golf;

import java.util.ArrayList;

import com.badlogic.gdx.physics.bullet.Bullet;

import engine.Game;
import engine.GameObject;
import engine.GameUniverse;

public class GolfGame extends Game {
	public static GameUniverse universe;
	public static Camera camera;
	public static Golfball ball;
	
	public ArrayList<GameObject> objects;
	
	@Override
	public void initialize(GameUniverse universe) {
		GolfGame.universe = universe;
		new SunEnvironment();
		Bullet.init();
		
		camera = new Camera();
		
		objects = new ArrayList<GameObject>();
		objects.add(ball = new Golfball());
	}
	
	@Override
	public void step(float deltaTime) {
		// Update all the objects
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).update(deltaTime);
		}
		
		// Update the camera
		camera.setEye( 8, 8, 8 );
		camera.update();
	}
}
