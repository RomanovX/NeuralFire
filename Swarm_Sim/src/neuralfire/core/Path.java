package neuralfire.core;

public class Path {
	private Field firstField;
	private Field secondField;
	private int pheromoneIntensity = 0;
	
	public Path(Field firstField, Field secondField){
		this.firstField = firstField;
		this.secondField = secondField;
	}

	public int getPheromoneIntensity() {
		return pheromoneIntensity;
	}

	public void incresePheromoneIntensity(int increaseBy) {
		this.pheromoneIntensity = pheromoneIntensity + increaseBy;
	}
	
	public Field getOtherField(Field currentField){
		return currentField.equals(firstField) ? secondField : firstField;
	}
	
	

}
