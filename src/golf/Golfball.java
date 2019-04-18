package golf;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

import engine.physics.PhysicsObject;

public class Golfball extends PhysicsObject {
	
	public Golfball() {
		super("Resources/Models/golfball.obj", new btSphereShape(1.0f));
		
		setPosition(new Point3f(0, 8, 0));
		
		this.setVelocity(new Vector3f(2,1,2));
	}

}
