
public class droid extends object{
	
	
	public droid(){
		movable = true;
		stackable = true;
	}
	
	public void runAI(Grid grid,int row, int col)
	{
		field currField = grid.getField(row, col);
		move(Dir.RIGHT,currField);
	}
	
	private void move(Dir dir, field Field)
	{
		field fieldToGo = Field.getAdjecentField(dir);
		if(fieldToGo.getPasseble() == true)
		{
			fieldToGo.AddObject(this);
		}
		else
		{
			Field.AddObject(this);
		}
	}
	
}