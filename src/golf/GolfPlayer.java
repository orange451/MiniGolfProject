package golf;

import java.awt.Point;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import engine.Game;
import engine.GameObject;
import engine.io.Mouse;

public class GolfPlayer extends GameObject {

	public float yaw;
	public float pitch;
	private Golfball ball;
	private boolean turnCamera;
	
	public GolfPlayer() {
		ball = new Golfball();
		Game.addObject(ball);
		
		turnCamera = true;
	}
	
	@Override
	public void update(float deltaTime) {

		// Get mouse delta
		Point delta = Game.mouse.getDelta();
		
		// Turn Camera logic
		if ( turnCamera ) {
			
			// Apply direction
			float SENS = 256;
			yaw += delta.x/SENS;
			pitch += delta.y/SENS;
			
			// Limit pitch
			if ( pitch < Math.PI/2+0.15f )
				pitch = (float)Math.PI/2+0.15f;
			if ( pitch > Math.PI-0.5f )
				pitch = (float)Math.PI-0.5f;
			
			// Reset mouse
			Game.mouse.setMouseLocation(100, 100);
		}
		
		// Toggle camera turn. Prepare for ball hit
		if ( Game.mouse.isMouseButtonPressed(Mouse.LEFT_MOUSE) )
			turnCamera = !turnCamera;
		
		// Hit ball TEMPORARY
		if ( Game.keyboard.isKeyPressed("Space") )
			hit(40);
		
		// Update the camera
		float dist = 28;
		Point3f to = getPosition();
		to.add(new Point3f(0,0.7f,0));
		float xx = (float)Math.cos(yaw)*(float)Math.sin(pitch);
		float zz = (float)Math.sin(yaw)*(float)Math.sin(pitch);
		float yy = (float)-Math.cos(pitch);
		Game.camera.setEye( to.x+xx*dist, to.y+yy*dist, to.z+zz*dist );
		Game.camera.setTo(to.x, to.y, to.z);
		Game.camera.update();
	}

	@Override
	public Point3f getPosition() {
		if ( ball != null ) {
			return ball.getPosition();
		}
		return new Point3f();
	}

	@Override
	public void setPosition(Point3f position) {
		//
	}

	public Golfball getBall() {
		return ball;
	}

	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void hit(float force) {
		float xx = (float) (Math.cos(GolfGame.player.getYaw()+Math.PI)*force);
		float zz = (float) (Math.sin(GolfGame.player.getYaw()+Math.PI)*force);
		
		Vector3f c = ball.getVelocity();
		ball.setVelocity(new Vector3f(c.x+xx, c.y, c.z+zz));
	}
}
