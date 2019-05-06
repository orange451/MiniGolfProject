package golf.hole;

import java.util.ArrayList;

import golf.GolfGame;

public class Course1 {
	public Hole getNextHole() {
		ArrayList<Class<? extends Hole>> holeClasses = getHoles();
		
		int index = getNextHoleIndex();
		if ( index == -1 )
			return null;
		
		try {
			return holeClasses.get(index).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private int getNextHoleIndex() {
		ArrayList<Class<? extends Hole>> holeClasses = getHoles();
		Hole currentHole = GolfGame.getHole();
		if ( currentHole == null )
			return 0;
		int index = holeClasses.indexOf(currentHole.getClass())+1;
		if ( index == -1 )
			return -1;
		if ( index >= holeClasses.size() )
			return -1;
		
		return index;
	}
	
	private ArrayList<Class<? extends Hole>> getHoles() {
		ArrayList<Class<? extends Hole>> ret = new ArrayList<Class<? extends Hole>>();
		ret.add(Hole1.class);
		ret.add(Hole2.class);
		ret.add(Hole3.class);
		ret.add(Hole4.class);
		ret.add(Hole5.class);
		return ret;
	}
}
