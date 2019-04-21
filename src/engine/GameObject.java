package engine;

import javax.vecmath.Point3f;

public abstract class GameObject {
	public abstract void update(float deltaTime);
	
	public abstract Point3f getPosition();
	public abstract void setPosition(Point3f position);
}
