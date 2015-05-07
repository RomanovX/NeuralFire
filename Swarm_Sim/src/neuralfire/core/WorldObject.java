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
	public void relay(Grid grid,int row, int col){
		
	}
	
	public boolean getMovable()
	{
		return this.movable;
	}
	
	public void spread(Grid grid,int row, int col, int radius, int intensity) 
	{
		int dist = 0;
		
		for(int x = row - radius;x < row + radius+1; x++)
		{
			for(int y = col - radius;y < col + radius+1; y++)
			{

				if((x >= 0) && (y >= 0) && (x <= grid.getGridRows()-1) && (y <= grid.getGridCols()-1))
				{
					dist = Math.abs(row - x) + Math.abs(col - y);
					if(dist <= radius)
					{
						if(grid.getField(row, col).isHasFire())
						{
							grid.getField(row-(row - x), col-(col - y)).setIntensity(intensity - dist);
						}
						if(grid.getField(row, col).isHasDroid())
						{
							grid.getField(row-(row - x), col-(col - y)).setVolume(intensity - dist);
						}
					}
				}
			}			
		}
	}
	
	
}
