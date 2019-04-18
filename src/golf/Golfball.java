package golf;

import javax.vecmath.Point3f;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import engine.physics.PhysicsObject;

public class Golfball extends PhysicsObject {
	
	public Golfball() {
		super("Resources/Models/golfball.obj", new btSphereShape(1.0f));
		
		setPosition(new Point3f(0, 4, 0));
	}

}
