public class fire extends object{
	public fire(){
		movable = false;
		stackable = false;
	}
	
	public void extinguish()
	{
		stackable = true;
	}
}
