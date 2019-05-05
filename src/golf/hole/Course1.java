package golf.hole;

import java.util.ArrayList;

import golf.GolfGame;

public class Course1 {
	public Hole getNextHole() {
		ArrayList<Class<? extends Hole>> holeClasses = getHoles();
		Hole currentHole = GolfGame.getHole();
		int index = holeClasses.indexOf(currentHole.getClass());
		if ( index == -1 )
			return null;
		if ( index+1 >= holeClasses.size() )
			return null;
		
		try {
			return holeClasses.get(index+1).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
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
