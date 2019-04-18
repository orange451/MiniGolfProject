package engine.physics;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3f;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btTriangleMesh;

public class PhysicsUtils {

	public static btTriangleMesh getShapeFromModel(BranchGroup model) {
		if ( model == null )
			return new btTriangleMesh();
		
		ArrayList<Point3f> vertices = new ArrayList<Point3f>();
		Enumeration<Node> children = model.getAllChildren();
		while ( children.hasMoreElements() ) {
			Node element = children.nextElement();
			if ( element instanceof Shape3D ) {
				Shape3D shape = (Shape3D)element;
				Geometry geo = shape.getGeometry();
				if ( geo instanceof TriangleArray ) {
					TriangleArray triangles = (TriangleArray)geo;
					int verts = triangles.getVertexCount();
					for (int i = 0; i < verts; i++) {
						Point3f vertex = new Point3f();
						triangles.getCoordinate(i, vertex);
						vertices.add(vertex);
					}
				}
			}
		}
		
		btTriangleMesh mesh = new btTriangleMesh();
		for (int i = 0; i < vertices.size(); i+=3) {
			Point3f t;
			
			t = vertices.get(i+0);
			Vector3 v0 = new Vector3(t.x, t.y, t.z);
			
			t = vertices.get(i+1);
			Vector3 v1 = new Vector3(t.x, t.y, t.z);
			
			t = vertices.get(i+2);
			Vector3 v2 = new Vector3(t.x, t.y, t.z);
			
			mesh.addTriangle(v0, v1, v2);
		}
		
		return mesh;
	}

}
