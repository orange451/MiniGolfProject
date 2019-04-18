package engine;

import java.awt.BorderLayout;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
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

		// Create a light that shines for 100m from the origin
		Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		mainGroup.addChild(light1);

		// Reset view & add to universe
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(mainGroup);
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