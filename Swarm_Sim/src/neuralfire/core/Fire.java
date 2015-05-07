package neuralfire.core;

public class Fire extends WorldObject{
	public Fire(){
		movable = false;
		stackable = false;
	}
	
	public void extinguish()
	{
		stackable = true;
	}
	
	public void runAI(Grid grid,int row, int col)
	{	
		this.spread(grid, row, col, Constants.fireRadius, Constants.fireIntensity);
		grid.getField(row, col).AddObject(this);		
	}
	
	
}
