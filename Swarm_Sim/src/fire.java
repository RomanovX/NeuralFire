public class fire extends object{
	public fire(){
		movable = false;
		stackable = false;
	}
	
	public void extinguish()
	{
		stackable = true;
	}
	
	public void runAI(Grid grid, int row, int col)
	{
		grid.getField(row, col);
	}
	
}
