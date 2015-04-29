import java.awt.Color;


public class object {
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
