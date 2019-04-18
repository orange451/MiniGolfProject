package engine;

public class GameThread {
	public static void start(Game callback, GameUniverse universe) {
		// Create game-loop to rotate the shape
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.nanoTime();

				while(true) {
					// Sleep thread (so CPU doesn't overwork)
					try {
						Thread.sleep(1000/60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Calculate the delta time
					long time = System.nanoTime();
					double delta = (time-start)*1.0e-9;
					start = time;
					
					// Send it back to the game
					callback.step((float) delta);
				}
			}
		}).start();
		
		callback.initialize(universe);
	}
}
