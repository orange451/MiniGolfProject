package engine.physics;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
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
import engine.GameObject;
import engine.obj.ObjModel;
import golf.GolfGame;

public abstract class PhysicsObject extends GameObject implements PhysicsInterf {
	private BranchGroup model;
	private btRigidBody body;
	private TransformGroup localTransform;
	
	public PhysicsObject(String filepath) {
		this(filepath, false);
	}
	
	public PhysicsObject(String filepath, boolean anchored) {
		this(filepath, anchored?0:0.5f);
	}
	
	public PhysicsObject(String filepath, float mass) {
		this(filepath, mass, null);
	}
	
	public PhysicsObject(String filepath, float mass, btCollisionShape shape) {
		try {
			//ObjectFile obj = new ObjectFile(ObjectFile.TRIANGULATE);
			//model = obj.load(new FileReader(filepath)).getSceneGroup();
			
			model = ObjModel.load(filepath);
			
			localTransform = new TransformGroup();
			localTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			localTransform.addChild(model);
			
			GolfGame.universe.getMainGroup().addChild( localTransform );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		if ( localTransform == null )
			return;
		
		// Set our position to the physics body position
		Matrix4 worldTransform = body.getWorldTransform();
		Transform3D transform = new Transform3D(worldTransform.getValues());
		transform.transpose();
		localTransform.setTransform(transform);
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
	public Matrix4f getWorldMatrix() {
		Transform3D transform = new Transform3D();
		localTransform.getTransform(transform);
		
		float[] vals = new float[16];
		transform.get(vals);
		return new Matrix4f(vals);
	}

	@Override
	public void setWorldMatrix(Matrix4f worldMatrix) {
		Transform3D transform = new Transform3D(worldMatrix);
		localTransform.setTransform(transform);
		
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
