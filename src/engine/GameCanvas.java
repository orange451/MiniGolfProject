package engine;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.concurrent.atomic.AtomicLong;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;

import com.sun.j3d.utils.universe.SimpleUniverse;

import engine.io.KeyListener;
import engine.io.MouseListener;

public class GameCanvas extends Panel implements GameUniverse {
	private static final long serialVersionUID = 1L;
	
	protected SimpleUniverse universe;
	protected BranchGroup mainGroup;
	protected Canvas3D canvas;

	public GameCanvas(Game callback) {
		setLayout(new BorderLayout());

		// Create 3d canvas
		AtomicLong start = new AtomicLong(System.nanoTime());
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration()) {
			private static final long serialVersionUID = 1L;

			@Override
			public void preRender() {

				// Calculate the delta time
				long time = System.nanoTime();
				double delta = (time-start.longValue())*1.0e-9;
				start.set((long) time);
				
				// Step the game before we render
				try {
					Game.keyboard.tick();
					Game.mouse.tick();
					callback.step((float) delta);
					Game.keyboard.endTick();
					Game.mouse.endTick();
				} catch(Exception e) {
					e.printStackTrace();
				}

				// Continue normal rendering
				super.preRender();
			}
			
			@Override
			public void postRender() {
				super.postRender();
				
				callback.paint(this.getGraphics2D());
				this.getGraphics2D().flush(true);
			}
		};
		this.add(canvas, BorderLayout.CENTER);
		
		// Universe
		universe = new SimpleUniverse(canvas);

		// Create a structure to contain objects
		mainGroup = new BranchGroup();
		mainGroup.setCapability(BranchGroup.ALLOW_DETACH);
		mainGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		mainGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
		
		callback.initialize(this);
		
		// Finalize
		universe.addBranchGraph(mainGroup);
		
		canvas.addKeyListener(new KeyListener());
		canvas.addMouseListener(new MouseListener());
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("A");
	}

	@Override
	public SimpleUniverse getUniverse() {
		return universe;
	}

	@Override
	public BranchGroup getMainGroup() {
		return mainGroup;
	}

	@Override
	public Canvas3D getCanvas() {
		return canvas;
	}
}