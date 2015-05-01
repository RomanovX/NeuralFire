package neuralfire.core;

public class Droid extends WorldObject{
	
	
	public Droid(){
		movable = true;
		stackable = true;
	}
	
	public void runAI(Grid grid,int row, int col)
	{
		Field currField = grid.getField(row, col);
		move(Constants.Dir.UP,currField);
	}
	
	private void move(Constants.Dir dir, Field Field)
	{
		Field fieldToGo = Field.getAdjecentField(dir);
		if(fieldToGo.getPasseble() == true)
		{
			fieldToGo.AddObject(this);
		}
		else
		{
			Field.AddObject(this);
		}
	}
	
	private void placeFeromone(Field Field, int info)
	{
		Field.AddObject(new Pheromone(info));
	}
	
}