package golf;

import engine.DrawableObject;
import engine.obj.ObjModel;

public class Putter extends DrawableObject {
	public Putter() {
		super(ObjModel.load("Resources/Models/putter.obj"));
	}

	@Override
	public void update(float deltaTime) {
		//
	}
}
