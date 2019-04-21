package golf;

import engine.obj.ObjModel;
import engine.physics.PhysicsObject;

public class Floor extends PhysicsObject {
	public Floor() {
		super(ObjModel.load("Resources/Models/floor.obj"), true);
	}
}
