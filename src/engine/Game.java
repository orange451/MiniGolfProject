package engine;

import engine.io.Keyboard;
import engine.io.Mouse;

public abstract class Game {
	public static GameUniverse universe;

	public static final Keyboard keyboard = new Keyboard();
	public static final Mouse mouse = new Mouse();
	
	public abstract void initialize(GameUniverse universe);
	public abstract void step(float deltaTime);
}
