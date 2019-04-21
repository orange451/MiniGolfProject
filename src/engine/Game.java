package engine;

import java.util.ArrayList;

import engine.io.Keyboard;
import engine.io.Mouse;

public abstract class Game {
	public static GameUniverse universe;
	public static Camera camera;
	private static Game game;

	protected ArrayList<GameObject> objects;

	public static final Keyboard keyboard = new Keyboard();
	public static final Mouse mouse = new Mouse();
	
	public abstract void initialize(GameUniverse universe);
	
	public Game() {
		camera = new Camera();
		objects = new ArrayList<GameObject>();
		Game.game = this;
	}
	
	public void step(float deltaTime) {
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
}
