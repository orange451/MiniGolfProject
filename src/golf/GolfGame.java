package golf;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.GeometryInfo;

import engine.Game;
import engine.GameUniverse;

public class GolfGame extends Game {

	@Override
	public void initialize(GameUniverse universe) {
		try {
			ObjectFile obj = new ObjectFile();
			Scene scene = obj.load("Resources/Models/golfball.obj");
			Node node = scene.getSceneGroup().getChild(0);
			scene.getSceneGroup().removeChild(node);
			
			universe.getMainGroup().addChild(node);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void step(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
