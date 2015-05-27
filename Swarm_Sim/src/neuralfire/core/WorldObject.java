package neuralfire.core;

import rlforj.los.ILosAlgorithm;
import rlforj.los.PrecisePermissive;

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
	
	public void spread(Grid grid,int row, int col, int radius, double intensity) 
	{
		int dist = 0;
		ILosAlgorithm a=new PrecisePermissive();
		
		
		for(int x = row - radius;x < row + radius+1; x++)
		{
			for(int y = col - radius;y < col + radius+1; y++)
			{

				if((x >= 0) && (y >= 0) && (x <= grid.getGridRows()-1) && (y <= grid.getGridCols()-1))
				{
					dist = Math.abs(row - x) + Math.abs(col - y);
					if(dist <= radius && a.existsLineOfSight(grid, row, col, x, y, false) && !grid.getField(x, y).isHasWall())
					//if(dist <= radius)
					{
						if(grid.getField(row, col).isHasDroid())
						{
							grid.getField(row-(row - x), col-(col - y)).setVolume(intensity - dist);
						}
						else if(grid.getField(row, col).isHasFire())
						{
							if(intensity == 0)
							{
								grid.getField(row-(row - x), col-(col - y)).clearIntensity();
							}
							else
							{
								grid.getField(row-(row - x), col-(col - y)).setIntensity(intensity - dist);
							}
						}
					}
				}
			}			
		}
	}
	
	
}
