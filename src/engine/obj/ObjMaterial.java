package engine.obj;

import javax.media.j3d.Texture;
import javax.vecmath.Color3f;

public class ObjMaterial {
	private Color3f diffuse;
	private Color3f ambient;
	private Color3f specular;
	private float shininess;
	public String name;
	public Texture texture;
	
	public ObjMaterial(String name) {
		this.name = name;
		this.texture = null;
		setDiffuse(1, 1, 1);
		setAmbient(0.2f,0.2f,0.2f);
		setSpecular(0.0f,0.0f,0.0f);
		setShininess(50);
	}
	
	public ObjMaterial setDiffuse(float r, float g, float b) {
		diffuse = new Color3f(r, g, b);
		return this;
	}
	
	public ObjMaterial setAmbient(float r, float g, float b) {
		ambient = new Color3f(r, g, b);
		return this;
	}
	
	public ObjMaterial setSpecular(float r, float g, float b) {
		specular = new Color3f(r, g, b);
		return this;
	}
	
	public ObjMaterial setShininess(float shininess) {
		this.shininess = shininess;
		return this;
	}
	
	public ObjMaterial setTexture(Texture texture) {
		this.texture = texture;
		return ObjMaterial.this;
	}
	
	public Color3f getDiffuse() {
		return diffuse;
	}
	
	public Color3f getAmbient() {
		return ambient;
	}
	
	public Color3f getSpecular() {
		return specular;
	}
	
	public float getShininess() {
		return this.shininess;
	}
	
	@Override
	public String toString() {
		return "ObjMaterial:"+name;
	}

	public Texture getTexture() {
		return texture;
	}
}