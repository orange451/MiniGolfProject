package golf;

import java.awt.Dimension;

import javax.swing.JFrame;

import engine.GameCanvas;
import engine.GameThread;
import engine.GameUniverse;

public class Main {
	public static void main(String[] args) {
		// Create the JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add 3d canvas
		GameCanvas c = new GameCanvas();
		c.setPreferredSize(new Dimension(640,480));
		frame.add(c);
		
		// Start the game
		GameThread.start(new GolfGame(), (GameUniverse)c);

		// Display application
		frame.pack();
		frame.setVisible(true);
	}
}
