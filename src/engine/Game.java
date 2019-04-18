package engine;

public abstract class Game {
	public abstract void initialize(GameUniverse universe);
	public abstract void step(float deltaTime);
}
