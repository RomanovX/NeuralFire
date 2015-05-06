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
		int totalPheromonoe = currField.getConcentratedPheromoneCount();
		
		Random generator = new Random(); 
		double chosenProb = generator.nextDouble();
		Constants.Dir direction = Constants.Dir.UP;
		
		
		double upProb = 0.25;
		double downProb = 0.25;
		double leftProb = 0.25;
		double rightProb = 0.25;
		if(currField.getUpPath() != null){
			double intensity = currField.getUpPath().getPheromoneIntensity() == 0 ? 1 : currField.getUpPath().getPheromoneIntensity();
			upProb = totalPheromonoe / intensity;
		}
		else{
			upProb = 0;
		}
		if(currField.getDownPath() != null){
			double intensity = currField.getDownPath().getPheromoneIntensity() == 0 ? 1 : currField.getDownPath().getPheromoneIntensity();
			downProb = totalPheromonoe / intensity;
		}
		else{
			downProb = 0;
		}
		if(currField.getLeftPath() != null){
			double intensity = currField.getLeftPath().getPheromoneIntensity() == 0 ? 1 : currField.getLeftPath().getPheromoneIntensity();
			leftProb = totalPheromonoe / intensity;
		}
		else{
			leftProb = 0;
		}
		if(currField.getRightPath() != null){
			double intensity = currField.getRightPath().getPheromoneIntensity() == 0 ? 1 : currField.getRightPath().getPheromoneIntensity();
			rightProb = totalPheromonoe / intensity;
		}
		else{
			rightProb = 0;
		}
		double weightedProb = upProb + downProb + leftProb + rightProb;
		upProb /= weightedProb;
		downProb /= weightedProb;
		leftProb /= weightedProb;
		rightProb /= weightedProb;
		if(chosenProb < upProb)
			direction = Constants.Dir.UP;
		else if (chosenProb < upProb+downProb )
			direction = Constants.Dir.DOWN;
		else if (chosenProb < upProb+downProb+leftProb)
			direction = Constants.Dir.LEFT;
		else 
			direction = Constants.Dir.RIGHT;
		
		move(direction,currField);
	}
	
	private void move(Constants.Dir dir, Field field)
	{
		field.traversePath(dir, this);
	}
	
	private void placeFeromone(Field Field, int info)
	{
		Field.AddObject(new Pheromone(info));
	}
	
}