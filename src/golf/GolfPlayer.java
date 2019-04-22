package golf;

import java.awt.Point;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import engine.Game;
import engine.GameObject;
import engine.io.Mouse;
import engine.sound.Sound;

public class GolfPlayer extends GameObject {

	public float yaw;
	public float pitch;
	private Golfball ball;
	private Putter putter;
	private boolean turnCamera;
	private int freezeCamera;

	private boolean canSwing;
	private float swingOffset;
	
	private boolean paused;
	
	private Sound hitSound;

	public GolfPlayer() {
		ball = new Golfball();
		Game.addObject(ball);

		putter = new Putter();
		Game.addObject(putter);

		turnCamera = true;
		
		hitSound = new Sound("Resources/Sounds/hit.wav");
	}

	@Override
	public void update(float deltaTime) {

		// Get mouse delta
		Point delta = Game.mouse.getDelta();

		// Turn Camera logic
		if ( !paused ) {
			if ( turnCamera ) {
				freezeCamera--;
				if ( freezeCamera < 0 ) {
					// Apply direction
					float SENS = 512;
					yaw += delta.x/SENS;
					pitch += delta.y/SENS;
	
					// Limit pitch
					if ( pitch < Math.PI/2+0.15f )
						pitch = (float)Math.PI/2+0.15f;
					if ( pitch > Math.PI-0.5f )
						pitch = (float)Math.PI-0.5f;
				}
	
				// Reset mouse
				boolean active = javax.swing.FocusManager.getCurrentManager().getActiveWindow() != null;
				if ( active )
					Game.mouse.setMouseLocation(100, 100);
			} else {
				// In putter swing mode...
				float ds = delta.y/32f;
				swingOffset += ds;
	
				// We hit the ball!
				if ( swingOffset < 0 ) {
					float force = Math.abs(ds)*32;
					if ( force > 250)
						force = 250;
	
					freezeCamera = 30;
					swingOffset = 0;
					turnCamera = true;
					hit(force);
				}
			}
		}

		// Compute if we can swing
		canSwing = ball.isStill();
		if ( !canSwing )
			turnCamera = true;

		// Position the putter
		{
			int offsetY = canSwing?0:99999;
			float pushBack = 1.0f+swingOffset;
			Vector3f ballPosition = getBall().getPosition();
			Matrix4f worldMatrix = new Matrix4f();
			worldMatrix.setIdentity();
			worldMatrix.rotY(-yaw+(float)Math.PI/2);
			worldMatrix.setTranslation(new Vector3f(ballPosition.x, ballPosition.y+offsetY-0.7f, ballPosition.z));

			Matrix4f pushMat = new Matrix4f();
			pushMat.setIdentity();
			pushMat.setTranslation(new Vector3f(0, 0, pushBack));
			worldMatrix.mul(pushMat);

			putter.setWorldMatrix(worldMatrix);
		}

		// Toggle camera turn. Prepare for ball hit
		if ( Game.mouse.isMouseButtonPressed(Mouse.LEFT_MOUSE) && canSwing )
			turnCamera = !turnCamera;
		
		if ( Game.keyboard.isKeyPressed("Escape") )
			paused = !paused;

		// Update the camera
		float dist = 28;
		Vector3f to = getPosition();
		to.add(new Point3f(0,0.7f,0));
		float xx = (float)Math.cos(yaw)*(float)Math.sin(pitch);
		float zz = (float)Math.sin(yaw)*(float)Math.sin(pitch);
		float yy = (float)-Math.cos(pitch);
		Game.camera.setEye( to.x+xx*dist, to.y+yy*dist, to.z+zz*dist );
		Game.camera.setTo(to.x, to.y, to.z);
		Game.camera.update();
	}

	@Override
	public Vector3f getPosition() {
		if ( ball != null ) {
			return ball.getPosition();
		}
		return new Vector3f();
	}

	@Override
	public void setPosition(Vector3f position) {
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
		
		hitSound.play();
	}
}
