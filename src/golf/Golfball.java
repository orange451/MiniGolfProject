package golf;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.loaders.objectfile.ObjectFile;

public class Golfball {
	
	public Golfball() {
		
		try {
			ObjectFile obj = new ObjectFile();
			BranchGroup model = obj.load("Resources/Models/golfball.obj").getSceneGroup();
			GolfGame.universe.getMainGroup().addChild( model );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
