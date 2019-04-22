package golf;

import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;

import engine.DrawableObject;
import engine.obj.ObjModel;

public class Putter extends DrawableObject {
	TransparencyAttributes t_attr;
	
	public Putter() {
		super(ObjModel.load("Resources/Models/putter.obj"));
		
		t_attr = new TransparencyAttributes();
		t_attr.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
		t_attr.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
		t_attr.setTransparencyMode(TransparencyAttributes.BLENDED);
		
		Enumeration<Node> shapes = this.model.getAllChildren();
		while(shapes.hasMoreElements()) {
			Node node = shapes.nextElement();
			if ( node instanceof Shape3D ) {
				((Shape3D)node).getAppearance().setTransparencyAttributes(t_attr);
			}
		}
	}

	@Override
	public void update(float deltaTime) {
		//
	}
	
	public void setTransparency(float transparency) {
		t_attr.setTransparency(transparency);
	}
}
