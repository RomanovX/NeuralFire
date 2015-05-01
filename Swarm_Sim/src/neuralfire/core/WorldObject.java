package neuralfire.core;

public class WorldObject {
	protected boolean movable;
	protected boolean stackable;
	
	public void runAI(Grid grid,int row, int col)
	{
		grid.getField(row, col).AddObject(this);
	}
	
	public void UpdateGraphics()
	{
		
	}
	
	public boolean getMovable()
	{
		return this.movable;
	}
	
	
}
