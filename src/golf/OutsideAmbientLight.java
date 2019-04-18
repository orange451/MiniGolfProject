package golf;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class OutsideAmbientLight {
	public OutsideAmbientLight() {
		// Main light
		{
			Color3f light1Color = new Color3f(0.75f, 0.75f, 0.7f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(4.0f, -6.0f, -12.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.65f, 0.65f, 0.6f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(-2.0f, -9.0f, 4.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.5f, 0.5f, 0.5f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(1.0f, 9.0f, -7.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.2f, 0.2f, 0.25f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(-8.0f, -3.0f, 0.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.6f, 0.55f, 0.55f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(2.0f, 3.0f, 8.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.3f, 0.3f, 0.25f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(-6.0f, 3.0f, 8.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.25f, 0.25f, 0.2f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(6.0f, -1.0f, 0.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
		
		// Create another light
		{
			Color3f light1Color = new Color3f(0.25f, 0.25f, 0.2f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1024.0);
			Vector3f light1Direction = new Vector3f(-6.0f, -1.0f, 0.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			GolfGame.universe.getMainGroup().addChild(light1);
		}
	}
}
