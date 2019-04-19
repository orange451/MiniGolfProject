package engine.obj;

import javax.media.j3d.Texture;
import javax.vecmath.Color3f;

public class ObjMaterial {
	private Color3f diffuse;
	public String name;
	public String textureName;
	public Texture texture;
	
	public ObjMaterial(String name) {
		this.name = name;
		this.texture = null;
		setDiffuse(1, 1, 1);
	}
	
	public ObjMaterial setDiffuse(float r, float g, float b) {
		diffuse = new Color3f(r, g, b);
		return ObjMaterial.this;
	}
	
	public ObjMaterial setTextureName(String name) {
		this.textureName = name;
		return ObjMaterial.this;
	}
	
	public Color3f getDiffuse() {
		return diffuse;
	}
	
	@Override
	public String toString() {
		return "ObjMaterial:"+name;
	}
}