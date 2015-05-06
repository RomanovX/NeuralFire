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
		int dist = 0;
		
		for(int x = row - Constants.fireRadius;x < row + Constants.fireRadius+1; x++)
		{
			for(int y = col - Constants.fireRadius;y < col + Constants.fireRadius+1; y++)
			{

				if((x >= 0) && (y >= 0) && (x <= grid.getGridRows()-1) && (y <= grid.getGridCols()-1))
				{
					dist = Math.abs(row - x) + Math.abs(col - y);
					if(dist <= Constants.fireRadius)
					{
						grid.getField(row-(row - x), col-(col - y)).setIntencity(Constants.fireRadius - dist);
					}
				}
			}			
		}
		
		grid.getField(row, col).AddObject(this);		
	}
}
