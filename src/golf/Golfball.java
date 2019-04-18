package golf;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3f;

import com.sun.j3d.loaders.objectfile.ObjectFile;

import engine.GameObject;

public class Golfball extends GameObject {
	private BranchGroup model;
	
	public Golfball() {
		
		try {
			ObjectFile obj = new ObjectFile();
			model = obj.load("Resources/Models/golfball.obj").getSceneGroup();
			GolfGame.universe.getMainGroup().addChild( model );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float deltaTime) {
		//model.
	}

	@Override
	public Point3f getPosition() {
		return new Point3f(0,0,0);
	}
}
