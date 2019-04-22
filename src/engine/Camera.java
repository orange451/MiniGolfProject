package engine;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import golf.GolfGame;

public class Camera {
	private Point3d eye;
	private Point3d to;
	private float near = 0.1f;
	private float far = 1024;
	private float fov = 74;

	public Camera() {
		eye = new Point3d(0,0,8);
		to = new Point3d(0,0,0);
	}

	public void setEye(float x, float y, float z) {
		eye.set(x, y, z);
	}

	public void setTo(float x, float y, float z) {
		to.set(x, y, z);
	}

	public void update() {
		TransformGroup tGroup = GolfGame.getUniverse().getUniverse().getViewingPlatform().getViewPlatformTransform();

	    Transform3D lookAt = new Transform3D();
	    lookAt.lookAt( eye, to, new Vector3d( 0.0, 1.0, 0.0) );
	    lookAt.invert();
		tGroup.setTransform(lookAt);
		
		View view = Game.getUniverse().getCanvas().getView();
		view.setBackClipDistance(far);
		view.setFrontClipDistance(near);
		view.setFieldOfView(Math.toRadians(fov));
	}
}
