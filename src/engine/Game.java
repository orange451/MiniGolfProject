package engine;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.io.Keyboard;
import engine.io.Mouse;
import engine.physics.PhysicsWorld;

public abstract class Game {
	public static GameUniverse universe;
	public static Camera camera;
	private static Game game;
	private static PhysicsWorld physicsWorld;

	protected ArrayList<GameObject> objects;

	public static final Keyboard keyboard = new Keyboard();
	public static final Mouse mouse = new Mouse();
	
	public abstract void initialize(GameUniverse universe);
	
	public Game() {
		Game.game = this;
		
		camera = new Camera();
		objects = new ArrayList<GameObject>();
		physicsWorld = new PhysicsWorld();
	}
	
	public static PhysicsWorld getPhysicsWorld() {
		return physicsWorld;
	}
	
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
	}
	
	public static void addObject(GameObject object) {
		game.objects.add(object);
	}

	public static GameObject[] getObjects() {
		return game.objects.toArray(new GameObject[game.objects.size()]);
	}

	public abstract void paint(Graphics2D g);
}
