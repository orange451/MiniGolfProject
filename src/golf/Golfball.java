package golf;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

import engine.Game;
import engine.physics.PhysicsObject;

public class Golfball extends PhysicsObject {
	
	public Golfball() {
		super("Resources/Models/golfball.obj", 0.5f, new btSphereShape(1.0f));
		
		setPosition(new Point3f(0, 8, 0));
		
		this.setVelocity(new Vector3f(2,1,1.5f));
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if ( Game.keyboard.isKeyPressed("Space") ) {
			this.setVelocity(new Vector3f(0,20,0));
		}
		
		// Increase damping the SLOWER we are (so it feels more linear when stopping)
		float speed = (float) Math.max(0.1, this.getVelocity().lengthSquared());
		float invSpeed = 1.0f/speed;
		this.getBody().setDamping(0.2f, invSpeed*0.3f);
	}
}
