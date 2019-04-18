package engine;

import java.awt.BorderLayout;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class GameCanvas extends JPanel implements GameUniverse {
	private static final long serialVersionUID = 1L;
	
	protected SimpleUniverse universe;
	protected BranchGroup mainGroup;

	public GameCanvas() {
		setLayout(new BorderLayout());

		// Create 3d canvas
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		this.add(canvas, BorderLayout.CENTER);

		// Universe
		universe = new SimpleUniverse(canvas);

		// Create a structure to contain objects
		mainGroup = new BranchGroup();
		mainGroup.setCapability(BranchGroup.ALLOW_DETACH);
		mainGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		mainGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
	}

	@Override
	public SimpleUniverse getUniverse() {
		return universe;
	}

	@Override
	public BranchGroup getMainGroup() {
		return mainGroup;
	}
}