package golf;

import javax.vecmath.Point3f;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

import engine.physics.PhysicsObject;

public class Golfball extends PhysicsObject {
	
	public Golfball() {
		super("Resources/Models/golfball.obj", 0.5f, new btSphereShape(1.0f));
		
		setPosition(new Point3f(0, 8, 0));
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		// Increase damping the SLOWER we are (so it feels more linear when stopping)
		float speed = (float) Math.max(0.1, this.getVelocity().length()/4f);
		float invSpeed = 1.0f/speed;
		this.getBody().setDamping(0.2f, invSpeed*0.3f);
	}
}
