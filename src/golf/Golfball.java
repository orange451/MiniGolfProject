package golf;

import javax.vecmath.Vector3f;

import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

import engine.Game;
import engine.obj.ObjModel;
import engine.physics.PhysicsObject;
import engine.sound.Sound;

public class Golfball extends PhysicsObject {

	private int stillTicks;
	private boolean onGround;
	private Sound cup;
	
	public Golfball() {
		super(ObjModel.load("Resources/Models/golfball.obj"), 0.5f, new btSphereShape(1.0f));
		
		setPosition(new Vector3f(0, 8, 0));
		
		cup = new Sound("Resources/sounds/cup.wav");
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		// Check if we're on the ground
		ClosestRayResultCallback callback = Game.getPhysicsWorld().rayTestExcluding(this.getPosition(), new Vector3f(0,-1.1f,0), this);
		onGround = callback.getCollisionObject() != null;
		
		// Increase damping the SLOWER we are (so it feels more linear when stopping)
		float speed = (float) Math.max(0.0, this.getVelocity().length()/3f);
		float invSpeed = Math.min(1, 1.0f/(float)Math.max(0.1, Math.min(speed,2)));
		this.getBody().setDamping(invSpeed*(onGround?0.9999f:0.1f), invSpeed*(onGround?0.4f:0.1f));
		
		// Compute if still or not
		if ( speed < 0.05 )
			stillTicks++;
		else
			stillTicks = 0;
		
		// Handle resetting
		if ( this.getPosition().y < -2 ) {
			this.setVelocity(new Vector3f());
			this.setPosition(new Vector3f(0, 4, 0));
			cup.play();
		}
	}
	
	public boolean isStill() {
		return stillTicks > 30;
	}
	
	public boolean isOnGround() {
		return onGround;
	}
}
