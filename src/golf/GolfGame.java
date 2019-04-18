package golf;

import engine.Game;
import engine.GameUniverse;

public class GolfGame extends Game {
	public static GameUniverse universe;
	
	@Override
	public void initialize(GameUniverse universe) {
		GolfGame.universe = universe;
		
		new Camera();
		new Golfball();
	}
	
	@Override
	public void step(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
