package golf;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import engine.physics.PhysicsObject;

public class Floor extends PhysicsObject {
	public Floor() {
		super("Resources/Models/floor.obj", new btBoxShape(new Vector3(16, 0.25f, 16)));
		getBody().setMassProps(0, new Vector3());
	}
}
