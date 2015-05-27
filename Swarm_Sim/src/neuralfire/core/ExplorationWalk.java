package neuralfire.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import neuralfire.core.Constants.Dir;

public class ExplorationWalk implements IWalkAlgorithm {

	@Override
	public Dir performWalk(Grid grid, int row, int col) {
		Field currentField = grid.getField(row, col);
		//double totalPheromonoe = currentField.getConcentratedPheromoneCount();
		
		
		Random generator = new Random(); 
		double chosenProb = generator.nextDouble();
		Constants.Dir direction = Constants.Dir.UP;
		
		HashMap<Constants.Dir, Double> probMap = new HashMap<Constants.Dir, Double>();
		probMap.put(Constants.Dir.UP, getPheromoneValue(currentField, Constants.Dir.UP));
		probMap.put(Constants.Dir.DOWN, getPheromoneValue(currentField, Constants.Dir.DOWN));
		probMap.put(Constants.Dir.LEFT, getPheromoneValue(currentField, Constants.Dir.LEFT));
		probMap.put(Constants.Dir.RIGHT, getPheromoneValue(currentField, Constants.Dir.RIGHT));
		
		double totalPheromone = 0;
		double countNonVisited = 0;
		HashMap.Entry<Dir, Double> pair;
		Iterator<Entry<Dir, Double>> it = probMap.entrySet().iterator();
	    while (it.hasNext()) {
	    	pair = (HashMap.Entry<Dir, Double>)it.next();
	        if(pair.getValue().equals(0.0))
	        	countNonVisited++;
	        else if(pair.getValue().equals(-1.0))
	        	it.remove();
	        else
	        	totalPheromone+= (Double)pair.getValue();
	    }
	    
	    if(countNonVisited > 0){
	    	it = probMap.entrySet().iterator();
		    while (it.hasNext()) {
		    	pair = (HashMap.Entry<Dir, Double>)it.next();
		        if(pair.getValue().equals(0.0))
		        	pair.setValue(1/countNonVisited);
		        else
		        	it.remove();
		    }
	    } else{
	    	
	    	double prob = 0;
	    	double totalWeightedProb = 0;
	    	
	    	it = probMap.entrySet().iterator();
		    while (it.hasNext()) {
		    	pair = (HashMap.Entry<Dir, Double>)it.next();
		    	prob = totalPheromone/(Double)pair.getValue();
		        pair.setValue(prob);
		        totalWeightedProb += prob;
		    }
	    	it = probMap.entrySet().iterator();
		    while (it.hasNext()) {
		    	pair = (HashMap.Entry<Dir, Double>)it.next();
		        pair.setValue((Double)pair.getValue() / totalWeightedProb);
		    }
	    }
	    it = probMap.entrySet().iterator();
	    double accumulatedProb = 0;
	    double probToCheck = 0;
	    while (it.hasNext()) {
	    	pair = (HashMap.Entry<Dir, Double>)it.next();
	    	probToCheck = (Double)pair.getValue() + accumulatedProb;
	    	if(chosenProb < probToCheck)
	    		return (Constants.Dir)pair.getKey();
	    	accumulatedProb+=(Double)pair.getValue();
	    }
	    return direction;
	}
	
	private double getPheromoneValue(Field field, Constants.Dir dir){
		if(field.getPath(dir) != null && field.getPath(dir).getOtherField(field).getPasseble()){
			return field.getPath(dir).getOtherField(field).getConcentratedPheromoneCount();
		}
		return -1.0;
	}

}
