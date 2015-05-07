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
		
		
		Constants.Dir chosenDirection;
		
		
		
		// TODO include this once firewalk is implemented
		if(doPathExploration(currField))
			chosenDirection = computePathExplorationMove(currField);
		else
			chosenDirection = computeFireWalk(grid, row, col);
		
		// TODO remove this once firewalk is implemented:
		// chosenDirection = computePathExplorationMove(currField);
		
		
		move(chosenDirection,currField);
	}
	
	private Constants.Dir computeFireWalk(Grid grid, int row, int col){
		// TODO implement
		
		this.spread(grid, row, col, Constants.yellRadius, grid.getField(row, col).getIntensity());
				
		return Constants.Dir.DOWN;
		
		
	}
	
	private boolean doPathExploration(Field currentField){
		// check if we perceive any fire
		return currentField.getPerceivedFireIntensity() == 0;
	}
	
	private Constants.Dir computePathExplorationMove(Field currentField){
		int totalPheromonoe = currentField.getConcentratedPheromoneCount();
		
		Random generator = new Random(); 
		double chosenProb = generator.nextDouble();
		Constants.Dir direction = Constants.Dir.UP;
		
		
		double upProb = 0.25;
		double downProb = 0.25;
		double leftProb = 0.25;
		double rightProb = 0.25;
		if(currentField.getUpPath() != null && currentField.getUpPath().getOtherField(currentField).getPasseble()){
			double intensity = currentField.getUpPath().getPheromoneIntensity() == 0 ? 1 : currentField.getUpPath().getPheromoneIntensity();
			upProb = totalPheromonoe / intensity;
		}
		else{
			upProb = 0;
		}
		if(currentField.getDownPath() != null && currentField.getDownPath().getOtherField(currentField).getPasseble()){
			double intensity = currentField.getDownPath().getPheromoneIntensity() == 0 ? 1 : currentField.getDownPath().getPheromoneIntensity();
			downProb = totalPheromonoe / intensity;
		}
		else{
			downProb = 0;
		}
		if(currentField.getLeftPath() != null && currentField.getLeftPath().getOtherField(currentField).getPasseble()){
			double intensity = currentField.getLeftPath().getPheromoneIntensity() == 0 ? 1 : currentField.getLeftPath().getPheromoneIntensity();
			leftProb = totalPheromonoe / intensity;
		}
		else{
			leftProb = 0;
		}
		if(currentField.getRightPath() != null && currentField.getRightPath().getOtherField(currentField).getPasseble()){
			double intensity = currentField.getRightPath().getPheromoneIntensity() == 0 ? 1 : currentField.getRightPath().getPheromoneIntensity();
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
		
		return direction;
	}
	
	
	private void move(Constants.Dir dir, Field field)
	{
		field.traversePath(dir, this);
	}
	

}