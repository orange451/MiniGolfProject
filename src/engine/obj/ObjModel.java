package engine.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.GeometryInfo;

import engine.io.FileIO;
import engine.io.FileUtils;

public class ObjModel {	
	public static BranchGroup load(String path) throws FileNotFoundException {
		
		// Open the file
		BufferedReader reader = FileIO.file_text_open_read(ObjModel.class.getClassLoader(), path);
		if (reader == null) { // Then try to look for it outside the jar!
			String str = new File(path).getAbsolutePath();
			reader = FileIO.file_text_open_read(str);
		}

		// File Not Found
		if (reader == null)
			throw new FileNotFoundException();

		ArrayList<Point3f> vertices = new ArrayList<Point3f>();
		ArrayList<Point2f> textures  = new ArrayList<Point2f>();
		ArrayList<Vector3f> normals  = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		HashMap<String,ObjMaterial> materials = null;

		ObjMaterial baseMaterial = new ObjMaterial("base");
		ObjMaterial currentMaterial = baseMaterial;
		
		BranchGroup model = new BranchGroup();

		String line;
		while ((line = FileIO.file_text_read_line(reader)) != null) {
			if (line == null || line.length() <= 0)
				continue;
			String[] lineSplit = line.split(" ");
			String prefix = lineSplit[0];

			if (prefix.equals("#")) {
				continue;
			} else if (prefix.equals("mtllib")) {
				materials = parseOBJMaterial(FileUtils.getFileDirectoryFromPath(path)+File.separator+lineSplit[1]);
			} else if (prefix.equals("v")) {
				vertices.add(parseOBJVertex(line));
			} else if (prefix.equals("vn")) {
				normals.add(parseOBJNormal(line));
			} else if (prefix.equals("vt")) {
				textures.add(parseOBJTexture(line));
			} else if (prefix.equals("f")) {
				parseOBJFace(indices, line);
			} else if (prefix.equals("usemtl")) {
				String tempName = lineSplit[1];
				
				if ( materials != null ) {
					// Grab the new material
					ObjMaterial newMaterial = materials.get(tempName);
					if ( newMaterial == null )
						newMaterial = baseMaterial;
				
					// Before we change to the new material, if we have any queued indices, pack them into a mesh.
					if (indices.size() > 0)
						writeOldVerticesToMesh(model, indices, vertices, normals, textures, currentMaterial);

					// Clear indices, set new material
					indices.clear();
					currentMaterial = newMaterial;
				}
			}
		}
		
		// If we have any queued indices, put them into a mesh
		if (indices.size() > 0)
			writeOldVerticesToMesh(model, indices, vertices, normals, textures, currentMaterial);

		// Close file
		FileIO.file_text_close(reader);
		reader = null;

		return model;
	}

	private static void writeOldVerticesToMesh(BranchGroup model, final ArrayList<Integer> indices, final ArrayList<Point3f> vertices, final ArrayList<Vector3f> normals, final ArrayList<Point2f> textures, final ObjMaterial currentMaterial) {
		System.out.println("Transferred vertices into model");
		
		int stride = 9;
		int tris = indices.size()/stride;
		int verts = tris*3;
		
		TriangleArray triangles = new TriangleArray(verts, TriangleArray.COORDINATES|TriangleArray.TEXTURE_COORDINATE_2|TriangleArray.NORMALS);
		
		for (int j = 0; j < tris; j++) {
			int i = j*stride;
			Point3f vertex1 = vertices.get(indices.get(i+0));
			Point3f vertex2 = vertices.get(indices.get(i+3));
			Point3f vertex3 = vertices.get(indices.get(i+6));

			Point2f tc1 = textures.get(indices.get(i+1));
			Point2f tc2 = textures.get(indices.get(i+4));
			Point2f tc3 = textures.get(indices.get(i+7));

			Vector3f normal1 = normals.get(indices.get(i+2));
			Vector3f normal2 = normals.get(indices.get(i+5));
			Vector3f normal3 = normals.get(indices.get(i+8));
			
			triangles.setCoordinate(j*3+0, vertex1);
			triangles.setCoordinate(j*3+1, vertex2);
			triangles.setCoordinate(j*3+2, vertex3);
			triangles.setTextureCoordinate(j*3+0, tc1);
			triangles.setTextureCoordinate(j*3+1, tc2);
			triangles.setTextureCoordinate(j*3+2, tc3);
			triangles.setNormal(j*3+0, normal1);
			triangles.setNormal(j*3+1, normal2);
			triangles.setNormal(j*3+2, normal3);
		}

		// Create geometry
		GeometryInfo geometryInfo = new GeometryInfo(triangles);
		Geometry result = geometryInfo.getGeometryArray();
		
		// Define colors
		Color3f ambient = currentMaterial.getAmbient();
		Color3f emissive = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f diffuse = currentMaterial.getDiffuse();
		Color3f specular = currentMaterial.getSpecular();
		float shininess = currentMaterial.getShininess();

		// Create material
		Appearance appearance = new Appearance();
		Material material = new Material(ambient, emissive, diffuse, specular, shininess);
		appearance.setMaterial(material);
		
		// Build model
		Shape3D shape = new Shape3D(result, appearance);
		model.addChild(shape);
	}

	private static void parseOBJFace(ArrayList<Integer> indices, String line) {
		String[] faceIndices = line.split(" ");
		
		for (int i = 0; i < 3; i++) {
			// The current cluster
			String cluster = faceIndices[i+1];
			String[] ind = cluster.split("/");
			
			// Write (VERTEX, TEXTURE, NORMAL) for each index.
			for (int j = 0; j < 3; j++) {
				int index = Integer.parseInt(ind[j]) - 1;
				indices.add(index);
			}
		}
	}
	
	private static HashMap<String,ObjMaterial> parseOBJMaterial(String materialFile) {
		HashMap<String,ObjMaterial> materials = new HashMap<String,ObjMaterial>();
		if (materialFile != null && materialFile.length() > 0) {
			BufferedReader reader = FileIO.file_text_open_read(ObjModel.class.getClassLoader(), materialFile);
			if (reader == null) { // Then try to look for it outside the jar!
				String str = new File(materialFile).getAbsolutePath();
				reader = FileIO.file_text_open_read(str);
			}

			ObjMaterial buildingMaterial = null;

			String line;
			while ((line = FileIO.file_text_read_line(reader)) != null) {
				if (buildingMaterial != null) {
					if (line != null && line.length() > 0) {
						if (line.trim().startsWith("Kd")) {
							String[] temp = line.substring(line.indexOf("Kd") + 3).split(" ");
							float r = (float) Double.parseDouble(temp[0]);
							float g = (float) Double.parseDouble(temp[1]);
							float b = (float) Double.parseDouble(temp[2]);

							buildingMaterial.setDiffuse(r, g, b);
						} else if (line.trim().startsWith("Ka")) {
							String[] temp = line.substring(line.indexOf("Ka") + 3).split(" ");
							float r = (float) Double.parseDouble(temp[0]);
							float g = (float) Double.parseDouble(temp[1]);
							float b = (float) Double.parseDouble(temp[2]);

							buildingMaterial.setAmbient(r, g, b);
						} else if (line.trim().startsWith("Ks")) {
							String[] temp = line.substring(line.indexOf("Ks") + 3).split(" ");
							float r = (float) Double.parseDouble(temp[0]);
							float g = (float) Double.parseDouble(temp[1]);
							float b = (float) Double.parseDouble(temp[2]);

							buildingMaterial.setSpecular(r, g, b);
						} else if (line.trim().startsWith("map_Kd")) {
							String temp = line.substring(line.indexOf("map_Kd") + 7);
							buildingMaterial.setTextureName(temp);
						}
					}
				}
				
				if (line.startsWith("newmtl")) {
					buildingMaterial = new ObjMaterial(line.substring(7));
					materials.put(buildingMaterial.name, buildingMaterial);
				}
			}

			FileIO.file_text_close(reader);
			reader = null;
		}
		return materials;
	}

	private static Point3f parseOBJVertex(String line) {
		String[] xyz = line.split(" ");
		float x = Float.parseFloat(xyz[1]);
		float y = Float.parseFloat(xyz[2]);
		float z = Float.parseFloat(xyz[3]);
		return new Point3f(x, y, z);
	}

	private static Point2f parseOBJTexture(String line) {
		String[] xy = line.split(" ");
		float x = Float.parseFloat(xy[1]);
		float y = Float.parseFloat(xy[2]);
		return new Point2f(x, y);
	}

	private static Vector3f parseOBJNormal(String line) {
		String[] xyz = line.split(" ");
		float x = Float.parseFloat(xyz[1]);
		float y = Float.parseFloat(xyz[2]);
		float z = Float.parseFloat(xyz[3]);
		return new Vector3f(x, y, z);
	}
}
