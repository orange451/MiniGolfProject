package engine.physics;

import javax.vecmath.Vector3f;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;

import engine.Game;
import engine.GameObject;

public class PhysicsWorld {
	public btDiscreteDynamicsWorld dynamicsWorld;
	
	public PhysicsWorld() {
		Bullet.init();
		
		// Setup physics world
		btCollisionConfiguration collisionConfiguration = new btDefaultCollisionConfiguration();
		btCollisionDispatcher dispatcher = new btCollisionDispatcher(collisionConfiguration);
		btConstraintSolver solver = new btSequentialImpulseConstraintSolver();
		Vector3 worldMin = new Vector3(-1000f,-1000f,-1000f);
		Vector3 worldMax = new Vector3(1000f,1000f,1000f);
		btAxisSweep3 sweepBP = new btAxisSweep3(worldMin, worldMax);
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, sweepBP, solver, collisionConfiguration);
		
		dynamicsWorld.setGravity(new Vector3(0, -60, 0));
	}

	public void step(float deltaTime) {
		if ( dynamicsWorld == null ) 
			return;
		dynamicsWorld.stepSimulation(1/60f, 4);
	}
	
	public ClosestRayResultCallback rayTest( Vector3f origin, Vector3f direction, PhysicsObject physObj ) {
		btRigidBody collisionObject = physObj.getBody();
		Vector3 from = new Vector3( origin.x, origin.y, origin.z );
		Vector3 to   = new Vector3( origin.x + direction.x, origin.y + direction.y, origin.z + direction.z );

		// Calculate ray transforms
		Matrix4 start = new Matrix4();
		start.idt();
		start.setTranslation(from);
		Matrix4 finish = new Matrix4();
		finish.idt();
		finish.setTranslation(to);
		ClosestRayResultCallback callback = new ClosestRayResultCallback( from, to );
		
		btCollisionWorld.rayTestSingle(start, finish, collisionObject, collisionObject.getCollisionShape(), collisionObject.getWorldTransform(), callback);
		
		return callback;
	}
	
	public ClosestRayResultCallback rayTestExcluding( Vector3f origin, Vector3f direction, PhysicsObject... excluding ) {
		//ArrayList<ClosestRayResultCallback> callbacks = new ArrayList<ClosestRayResultCallback>();
		float maxDist = Float.MAX_VALUE;
		ClosestRayResultCallback ret = new ClosestRayResultCallback(new Vector3(origin.x, origin.y, origin.z), new Vector3(origin.x, origin.y, origin.z));
		
		// Get list of all objects in world
		GameObject[] objects = Game.getObjects();
		
		// Test ALL objects in the world
		for (int i = 0; i < objects.length; i++) {
			GameObject obj = objects[i];
			if ( obj instanceof PhysicsObject ) {
				PhysicsObject phys = (PhysicsObject) obj;

				// Check if we exclude this object from the ray-test
				boolean exclude = false;
				if ( excluding != null ) {
					for (int j = 0; j < excluding.length && !exclude; j++) {
						if ( excluding[j].equals(phys) ) {
							exclude = true;
						}
					}
					// If we exclude it. Do not ray-test
					if ( exclude )
						continue;
				}

				// Raytest
				ClosestRayResultCallback c = rayTest( origin, direction, phys );
				if ( c.hasHit() ) {
					Vector3 vec = new Vector3();
					c.getRayFromWorld(new Vector3());
					
					Vector3 hit = new Vector3();
					c.getHitPointWorld(hit);
					
					vec.sub(hit);
					
					float dist = vec.len();
					if ( dist < maxDist ) {
						maxDist = dist;
						ret = c;
					}
				}
			}
		}

		return ret;
	}
}
