package golf.hole;

import java.io.File;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Vector3f;

import engine.Game;
import engine.GameObject;
import engine.io.FileIO;
import engine.obj.ObjModel;
import engine.physics.PhysicsObject;

public abstract class Hole extends GameObject {
	
	private Vector3f startingPosition;
	private BranchGroup holeModel;
	
	public Hole(String directory) {
		String modelFile = directory + File.separator + "Hole.obj";
		String startFile = directory + File.separator + "start.txt";
		
		String[] startData = FileIO.file_text_read_line(FileIO.file_text_open_read(startFile)).split(",");
		startingPosition = new Vector3f(Float.parseFloat(startData[0]), Float.parseFloat(startData[1]), Float.parseFloat(startData[2]));
		
		this.holeModel = ObjModel.load(modelFile);
	}
	
	public void create() {
		Game.addObject(this);
		Game.addObject(new PhysicsObject(getHoleModel(), true) {});
	}
	
	@Override
	public Vector3f getPosition() {
		return new Vector3f();
	}

	@Override
	public void setPosition(Vector3f position) {
		//
	}
	
	public Vector3f getStartingPosition() {
		return this.startingPosition;
	}
	
	public BranchGroup getHoleModel() {
		return this.holeModel;
	}
}
