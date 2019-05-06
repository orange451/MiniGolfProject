package golf;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import engine.GameCanvas;

public class Main {

	public static void main(String[] args) {

		// Create the JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.setLayout(new BorderLayout());

		// Add 3d canvas
		GameCanvas c = new GameCanvas(new GolfGame());
		c.setPreferredSize(new Dimension(640,480));
		frame.setContentPane(c);

		// Display application
		frame.pack();
		frame.setVisible(true);
	}
}