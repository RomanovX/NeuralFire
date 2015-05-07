package neuralfire.core;

import neuralfire.core.Constants.Dir;

public class YellWalk implements IWalkAlgorithm {

	@Override
	public Dir performWalk(Grid grid, int row, int col) {
		Field currentField = grid.getField(row, col); 
		double maxVolume = grid.getField(row, col).getVolume();;
		Constants.Dir chosenDir = Constants.Dir.STAY;
		
		if(currentField.getUpPath() != null 
				&& currentField.getUpPath().getOtherField(currentField).getPasseble() 
				&& currentField.getUpPath().getOtherField(currentField).getVolume() > maxVolume){
			chosenDir = Constants.Dir.UP;
			maxVolume = currentField.getUpPath().getOtherField(currentField).getVolume();
		}
		if(currentField.getDownPath() != null 
				&& currentField.getDownPath().getOtherField(currentField).getPasseble() 
				&& currentField.getDownPath().getOtherField(currentField).getVolume() > maxVolume){
			chosenDir = Constants.Dir.DOWN;
			maxVolume = currentField.getDownPath().getOtherField(currentField).getVolume();
		}
		if(currentField.getLeftPath() != null 
				&& currentField.getLeftPath().getOtherField(currentField).getPasseble() 
				&& currentField.getLeftPath().getOtherField(currentField).getVolume() > maxVolume){
			chosenDir = Constants.Dir.LEFT;
			maxVolume = currentField.getLeftPath().getOtherField(currentField).getVolume();
		}
		if(currentField.getRightPath() != null 
				&& currentField.getRightPath().getOtherField(currentField).getPasseble() 
				&& currentField.getRightPath().getOtherField(currentField).getVolume() > maxVolume){
			chosenDir = Constants.Dir.RIGHT;
			maxVolume = currentField.getRightPath().getOtherField(currentField).getVolume();
		}
		
		return chosenDir;
	}

}
