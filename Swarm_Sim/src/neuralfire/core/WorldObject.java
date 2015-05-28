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
		
		
		for(int y = row - radius;y < row + radius+1; y++)
		{
			for(int x = col - radius;x < col + radius+1; x++)
			{
				int maxrow = grid.getGridRows();
				int maxcol = grid.getGridCols();
				
				if((x >= 0) && (y >= 0) && (y <= maxrow-1) && (x <= maxcol-1))
				{
					dist = Math.abs(row - y) + Math.abs(col - x);
					if(dist <= radius && a.existsLineOfSight(grid, row, col, y, x, false) && !grid.getField(y, x).isHasWall())
					//if(dist <= radius)
					{
						if(grid.getField(row, col).isHasDroid())
						{
							grid.getField(row-(row - y), col-(col - x)).setVolume(intensity - dist);
						}
						else if(grid.getField(row, col).isHasFire())
						{
							if(intensity == 0)
							{
								grid.getField(row-(row - y), col-(col - x)).clearIntensity();
							}
							else
							{
								grid.getField(row-(row - y), col-(col - x)).setIntensity(intensity - dist);
							}
						}
					}
				}
			}			
		}
	}
	
	
}
