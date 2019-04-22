package engine;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import golf.GolfGame;

public abstract class DrawableObject extends GameObject {
	protected BranchGroup model;
	private TransformGroup localTransform;
	
	public DrawableObject(BranchGroup model) {
		this.model = model;
		
		localTransform = new TransformGroup();
		localTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		localTransform.addChild(model);
		
		GolfGame.getUniverse().getMainGroup().addChild( localTransform );
	}

	@Override
	public Vector3f getPosition() {
		Matrix4f worldMatrix = getWorldMatrix();
		return new Vector3f( worldMatrix.m03, worldMatrix.m13, worldMatrix.m23);
	}

	@Override
	public void setPosition(Vector3f position) {
		Matrix4f transform = this.getWorldMatrix();
		transform.setTranslation(position);
		setWorldMatrix(transform);
	}
	
	public Matrix4f getWorldMatrix() {
		Transform3D transform = new Transform3D();
		localTransform.getTransform(transform);
		
		float[] vals = new float[16];
		transform.get(vals);
		return new Matrix4f(vals);
	}
	
	public void setWorldMatrix(Matrix4f worldMatrix) {
		Transform3D transform = new Transform3D(worldMatrix);
		localTransform.setTransform(transform);
	}
}
