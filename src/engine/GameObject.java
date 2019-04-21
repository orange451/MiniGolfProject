package engine;

import javax.vecmath.Vector3f;

public abstract class GameObject {
	public abstract void update(float deltaTime);
	
	public abstract Vector3f getPosition();
	public abstract void setPosition(Vector3f position);
}
