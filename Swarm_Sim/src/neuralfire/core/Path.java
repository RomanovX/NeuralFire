package neuralfire.core;

public class Path {
	private Field firstField;
	private Field secondField;
	private double pheromoneIntensity = 0;
	private double oldPheromoneIntensity = 0;
	private double pheromoneIncrease = 0;
	private int droidsPassed = 0;
	public static double currentMaxPheromone = 1;
	
	
	public Path(Field firstField, Field secondField){
		this.firstField = firstField;
		this.secondField = secondField;
	}

	public double getPheromoneIntensity() {
		return pheromoneIntensity;
	}

	private void incresePheromoneIntensity(double increaseBy) {
		//this.pheromoneIntensity = pheromoneIntensity + increaseBy;
		pheromoneIncrease += increaseBy;
		currentMaxPheromone = Math.max(currentMaxPheromone, this.pheromoneIntensity);
	}
	
	public boolean traverse(Field currField, WorldObject traversingObject){
		if(droidsPassed < Constants.maxDroidPerField && getOtherField(currField).getPasseble()){
			getOtherField(currField).AddObject(traversingObject);
			incresePheromoneIntensity(Constants.pheromoneIncrease);
			droidsPassed++;
			return true;
		}
		return false;
	}
	
	public Field getOtherField(Field currentField){
		return currentField.equals(firstField) ? secondField : firstField;
	}

	public void doPathLogic(){
		doPheromoneUpdate();
		droidsPassed = 0;
	}
	
	private void doPheromoneUpdate(){
		pheromoneIntensity = (1 - Constants.pheromoneDecay)*oldPheromoneIntensity+ pheromoneIncrease;
		if( pheromoneIntensity < Constants.pheromoneZeroThreshold)
			pheromoneIntensity = 0;
		oldPheromoneIntensity = pheromoneIntensity;
		pheromoneIncrease = 0;
	}

}
