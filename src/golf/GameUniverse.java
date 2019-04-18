package golf;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.universe.SimpleUniverse;

public interface GameUniverse {
	public SimpleUniverse getUniverse();
	public BranchGroup getMainGroup();
}
