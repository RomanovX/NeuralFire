package neuralfire.core;

public class Path {
	private Field firstField;
	private Field secondField;
	private double pheromoneIntensity = 0;
	private double oldPheromoneIntensity = 0;
	private double pheromoneIncrease = 0;
	public static double currentMaxPheromone = 1;
	
	public Path(Field firstField, Field secondField){
		this.firstField = firstField;
		this.secondField = secondField;
	}

	public double getPheromoneIntensity() {
		return pheromoneIntensity;
	}

	public void incresePheromoneIntensity(double increaseBy) {
		//this.pheromoneIntensity = pheromoneIntensity + increaseBy;
		pheromoneIncrease += increaseBy;
		if(currentMaxPheromone < this.pheromoneIntensity)
			currentMaxPheromone = this.pheromoneIntensity;
	}
	
	public Field getOtherField(Field currentField){
		return currentField.equals(firstField) ? secondField : firstField;
	}

	
	public void doPheromoneUpdate(){
		pheromoneIntensity = (1 - Constants.pheromoneDecay)*oldPheromoneIntensity+ pheromoneIncrease;
		if( pheromoneIntensity < Constants.pheromoneZeroThreshold)
			pheromoneIntensity = 0;
		oldPheromoneIntensity = pheromoneIntensity;
		pheromoneIncrease = 0;
	}

}
