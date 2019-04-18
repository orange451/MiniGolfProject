package engine.physics;

import javax.vecmath.Matrix4f;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public interface PhysicsInterf {
	public Matrix4f getWorldMatrix();
	public void setWorldMatrix(Matrix4f worldMatrix);
	public btRigidBody getBody();
}
