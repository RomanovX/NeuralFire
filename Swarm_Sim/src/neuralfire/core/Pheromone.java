package neuralfire.core;

public class Pheromone extends WorldObject {
	
	int info;
	
	public Pheromone(int Info){
		movable = false;
		stackable = false;
		info = Info;
	}
	
	public int getInfo(){
		return info;
	}
}
