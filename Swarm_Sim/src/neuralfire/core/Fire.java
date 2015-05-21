package neuralfire.core;

import neuralfire.core.Constants.Dir;

public class Fire extends WorldObject{
	public static int fireNo = 0;

	public Fire(){
		movable = false;
		stackable = false;
		fireNo++;
	}

	
	public void extinguish()
	{
		stackable = true;
	}
	
	public void runAI(Grid grid,int row, int col)
	{	
		int droids = 0;
		Field thisFire = grid.getField(row, col);
		
		droids += thisFire.getAdjecentField(Dir.UP).getDroidCounter();
		droids += thisFire.getAdjecentField(Dir.DOWN).getDroidCounter();
		droids += thisFire.getAdjecentField(Dir.LEFT).getDroidCounter();
		droids += thisFire.getAdjecentField(Dir.RIGHT).getDroidCounter();
		
		if(droids < Constants.droidstokillfire){
			this.spread(grid, row, col, Constants.fireRadius, Constants.fireIntensity);
			grid.getField(row, col).AddObject(this);
		}
		else {
			this.spread(grid, row, col, Constants.fireRadius, 0);
			fireNo--;
		}	
	}
	
	
}
