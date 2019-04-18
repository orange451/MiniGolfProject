package engine;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public interface GameUniverse {
	public SimpleUniverse getUniverse();
	public BranchGroup getMainGroup();
	public Canvas3D getCanvas();
}
