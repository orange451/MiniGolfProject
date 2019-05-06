package golf.menu;

import engine.DrawableObject;
import engine.obj.ObjModel;

public class GrassPiece extends DrawableObject {

	public GrassPiece() {
		super(ObjModel.load("Resources/Models/floor.obj"));
	}

	@Override
	public void update(float deltaTime) {
		//
	}

}
