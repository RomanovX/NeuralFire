
public class feromone extends object {
	
	int info;
	
	public feromone(int Info){
		movable = false;
		stackable = false;
		info = Info;
	}
	
	public int getInfo(){
		return info;
	}
}
