package golf;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class Camera {
	public Camera() {
		  TransformGroup VpTG = GolfGame.universe.getUniverse().getViewingPlatform().getViewPlatformTransform();

		  Transform3D Trfcamera = new Transform3D();
		  Trfcamera.setTranslation(new Vector3f(0.0f, 0.0f, 8));  
		  VpTG.setTransform( Trfcamera );
	}
}
