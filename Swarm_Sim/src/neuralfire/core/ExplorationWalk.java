package neuralfire.core;

import java.util.Random;

import neuralfire.core.Constants.Dir;

public class ExplorationWalk implements IWalkAlgorithm {

	@Override
	public Dir performWalk(Grid grid, int row, int col) {
		Field currentField = grid.getField(row, col);
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

}