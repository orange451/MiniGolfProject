package engine;

import java.awt.BorderLayout;
import java.util.concurrent.atomic.AtomicLong;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.swing.JPanel;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class GameCanvas extends JPanel implements GameUniverse {
	private static final long serialVersionUID = 1L;
	
	public static final int DESIRED_RATE = 60;
	
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

				callback.step((float) delta);
				super.preRender();
			}
		};
		this.add(canvas, BorderLayout.CENTER);
		
		// Universe
		universe = new SimpleUniverse(canvas);
		universe.getViewer().getView().setMinimumFrameCycleTime(1000/DESIRED_RATE);

		// Create a structure to contain objects
		mainGroup = new BranchGroup();
		mainGroup.setCapability(BranchGroup.ALLOW_DETACH);
		mainGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		mainGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
		
		callback.initialize(this);
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