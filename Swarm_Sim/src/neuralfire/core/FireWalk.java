package neuralfire.core;

import neuralfire.core.Constants.Dir;

public class FireWalk implements IWalkAlgorithm {

	@Override
	public Dir performWalk(Grid grid, int row, int col) {
		
		
		Field currentField = grid.getField(row, col); 
		int maxIntensity = grid.getField(row, col).getIntensity();;
		Constants.Dir chosenDir = Constants.Dir.STAY;
		
		if(currentField.getUpPath() != null 
				&& currentField.getUpPath().getOtherField(currentField).getPasseble() 
				&& currentField.getUpPath().getOtherField(currentField).getIntensity() > maxIntensity){
			chosenDir = Constants.Dir.UP;
			maxIntensity = currentField.getUpPath().getOtherField(currentField).getIntensity();
		}
		if(currentField.getDownPath() != null 
				&& currentField.getDownPath().getOtherField(currentField).getPasseble() 
				&& currentField.getDownPath().getOtherField(currentField).getIntensity() > maxIntensity){
			chosenDir = Constants.Dir.DOWN;
			maxIntensity = currentField.getDownPath().getOtherField(currentField).getIntensity();
		}
		if(currentField.getLeftPath() != null 
				&& currentField.getLeftPath().getOtherField(currentField).getPasseble() 
				&& currentField.getLeftPath().getOtherField(currentField).getIntensity() > maxIntensity){
			chosenDir = Constants.Dir.LEFT;
			maxIntensity = currentField.getLeftPath().getOtherField(currentField).getIntensity();
		}
		if(currentField.getRightPath() != null 
				&& currentField.getRightPath().getOtherField(currentField).getPasseble() 
				&& currentField.getRightPath().getOtherField(currentField).getIntensity() > maxIntensity){
			chosenDir = Constants.Dir.RIGHT;
			maxIntensity = currentField.getRightPath().getOtherField(currentField).getIntensity();
		}
		
		return chosenDir;
	}

}
