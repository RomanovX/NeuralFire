package neuralfire.core;

import java.util.Random;

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
			thisFire.clearFireCounter();
		}	
		
		if(thisFire.getFireCounter() > Constants.timeToSpread)
		{
			Random rand = new Random();
			if(!thisFire.getAdjecentField(Dir.UP).isHasFire() 
					&& !thisFire.getAdjecentField(Dir.UP).isHasDroid() 
					&& !thisFire.getAdjecentField(Dir.UP).isHasWall()
					&& rand.nextBoolean())
				thisFire.getAdjecentField(Dir.UP).AddObject(new Fire());
			if(!thisFire.getAdjecentField(Dir.DOWN).isHasFire() 
					&& !thisFire.getAdjecentField(Dir.DOWN).isHasDroid() 
					&& !thisFire.getAdjecentField(Dir.DOWN).isHasWall()
					&& rand.nextBoolean())
				thisFire.getAdjecentField(Dir.DOWN).AddObject(new Fire());
			if(!thisFire.getAdjecentField(Dir.LEFT).isHasFire() 
					&& !thisFire.getAdjecentField(Dir.LEFT).isHasDroid() 
					&& !thisFire.getAdjecentField(Dir.LEFT).isHasWall()
					&& rand.nextBoolean())
				thisFire.getAdjecentField(Dir.LEFT).AddObject(new Fire());
			if(!thisFire.getAdjecentField(Dir.RIGHT).isHasFire() 
					&& !thisFire.getAdjecentField(Dir.RIGHT).isHasDroid() 
					&& !thisFire.getAdjecentField(Dir.RIGHT).isHasWall()
					&& rand.nextBoolean())
				thisFire.getAdjecentField(Dir.RIGHT).AddObject(new Fire());
			thisFire.clearFireCounter();
		}
	}
	
	
}
