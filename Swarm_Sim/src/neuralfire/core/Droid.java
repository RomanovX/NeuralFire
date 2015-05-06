package neuralfire.core;

import java.util.Random;

public class Droid extends WorldObject{
	
	
	public Droid(){
		movable = true;
		stackable = true;
	}
	
	public void runAI(Grid grid,int row, int col)
	{
		Field currField = grid.getField(row, col);
		
		Random generator = new Random(); 
		int i = generator.nextInt(4)+1;
		Constants.Dir direction = Constants.Dir.UP;
		switch(i){
		case 1:
			direction = Constants.Dir.UP;
			break;
		case 2:
			direction = Constants.Dir.DOWN;
			break;
		case 3:
			direction = Constants.Dir.LEFT;
			break;
		case 4:
			direction = Constants.Dir.RIGHT;
			break;
		}
		
		move(direction,currField);
	}
	
	private void move(Constants.Dir dir, Field field)
	{
		field.traversePath(dir, this);
		/*
		Field fieldToGo = Field.getAdjecentField(dir);
		if(fieldToGo.getPasseble() == true)
		{
			fieldToGo.AddObject(this);
		}
		else
		{
			Field.AddObject(this);
		}
		*/
	}
	
	private void placeFeromone(Field Field, int info)
	{
		Field.AddObject(new Pheromone(info));
	}
	
}