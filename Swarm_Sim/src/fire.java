public class fire extends object{
	public fire(){
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
		
		for(int x = row - 10;x < row + 11; x++)
		{
			for(int y = col - 10;y < col + 11; y++)
			{
				if((x >= 0) && (y >= 0) && (x <= GRIDROWS-1) && (y <= 99))
				{
					dist = Math.abs(row - x) + Math.abs(col - y);
					if(dist <= 10)
					{
						grid.getField(row-(row - x), col-(col - y)).setIntencity(dist);
					}
				}
			}			
		}
		
		grid.getField(row, col).AddObject(this);		
	}
}
