package engine.physics;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btGImpactMeshShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.linearmath.btDefaultMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

import engine.DrawableObject;
import golf.GolfGame;

public abstract class PhysicsObject extends DrawableObject implements PhysicsInterf {
	private btRigidBody body;
	
	public PhysicsObject(BranchGroup model) {
		this(model, false);
	}
	
	public PhysicsObject(BranchGroup model, boolean anchored) {
		this(model, anchored?0:0.5f);
	}
	
	public PhysicsObject(BranchGroup model, float mass) {
		this(model, mass, null);
	}
	
	public PhysicsObject(BranchGroup model, float mass, btCollisionShape shape) {
		super(model);
		
		if ( shape == null ) {
			// GImpact if needs mass
			if ( mass > 0 ) {
				shape = new btGImpactMeshShape(PhysicsUtils.getShapeFromModel(model));
			} else {
				// Anchored physics object
				shape = new btBvhTriangleMeshShape(PhysicsUtils.getShapeFromModel(model), true);
			}
		}
		
		btMotionState bodyMotionState = new btDefaultMotionState(new Matrix4().idt());
		Vector3 ballInertia = new Vector3();
		if ( mass > 0 )
			shape.calculateLocalInertia(mass, ballInertia);
		
		btRigidBodyConstructionInfo bodyInfo = new btRigidBodyConstructionInfo(mass, bodyMotionState, shape, ballInertia);
		bodyInfo.setRestitution(0.8f);
		bodyInfo.setFriction(0.7f);
		
		body = new btRigidBody(bodyInfo);
		body.setActivationState(1);
		body.activate(true);
		GolfGame.physicsWorld.dynamicsWorld.addRigidBody(body);
	}
	
	@Override
	public void update(float deltaTime) {
		// Set our position to the physics body position
		Matrix4 worldTransform = body.getWorldTransform();
		Matrix4f transform = new Matrix4f(worldTransform.getValues());
		transform.transpose();
		setWorldMatrix(transform);
	}

	@Override
	public Point3f getPosition() {
		Matrix4f worldMatrix = getWorldMatrix();
		return new Point3f( worldMatrix.m03, worldMatrix.m13, worldMatrix.m23);
	}

	@Override
	public void setPosition(Point3f position) {
		Matrix4f worldMatrix = getWorldMatrix();
		worldMatrix.setTranslation(new Vector3f(position.x, position.y, position.z));
		setWorldMatrix(worldMatrix);
	}

	@Override
	public void setWorldMatrix(Matrix4f worldMatrix) {
		// Use parents set world matrix function for drawing
		super.setWorldMatrix(worldMatrix);
		
		// Store matrix into physics object as well
		Transform3D temp = new Transform3D(worldMatrix);
		temp.transpose();
		float[] vals = new float[16];
		temp.get(vals);
		Matrix4 worldTrans = new Matrix4(vals);
		body.setWorldTransform(worldTrans);
	}

	@Override
	public btRigidBody getBody() {
		return body;
	}
	

	@Override
	public void setVelocity(Vector3f vector) {
		body.activate(true);
		body.setLinearVelocity(new Vector3(vector.x, vector.y, vector.z));
	}

	@Override
	public Vector3f getVelocity() {
		Vector3 vel = body.getLinearVelocity();
		return new Vector3f( vel.x, vel.y, vel.z);
	}
}
