package engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;

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
		
		dynamicsWorld.setGravity(new Vector3(0, -40, 0));
	}

	public void step(float deltaTime) {
		if ( dynamicsWorld == null ) 
			return;
		dynamicsWorld.stepSimulation(deltaTime, 2);
	}
}
