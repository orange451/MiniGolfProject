package engine.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public interface PhysicsInterf {
	public Matrix4f getWorldMatrix();
	public void setWorldMatrix(Matrix4f worldMatrix);
	public btRigidBody getBody();
	
	public void setVelocity( Vector3f vector );
	public Vector3f getVelocity();
}
