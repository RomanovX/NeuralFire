import java.awt.Color;
import java.util.ArrayList;


public class field {
	private ArrayList<object> ObjList = new ArrayList<object>();
	private int intencity;
	int col;
	int row;
	private boolean hasFire;
	private boolean hasDroid;
	private boolean hasWall;
	
	public field(int Row,int Col){
		intencity = 0;
		hasFire = false;
		hasDroid = false;
		hasWall = false;
		col = Col;
		row = Row;

	}
	
	public void AddObject(object x)	{
		ObjList.add(x);
	}
	
	public void RemoveObjext(object x)	{
		ObjList.remove(x);
	}
	
	public void UpdateField()	{
		hasFire = false;
		hasDroid = false;
		hasWall = false;
		for(object x: ObjList){
			if(x instanceof fire)
				hasFire = true;
			
			if(x instanceof droid)
				hasDroid = true;
			
			if(x instanceof wall)
				hasWall = true;
			
			x.runAI();
		}
	}
	
	public Color GetColor(){
		if(hasDroid)
			return Color.green;
		
		if(hasFire)
			return Color.red;
		
		if(hasWall)
			return Color.black;
		
		if(intencity > 0)
			return Color.yellow;
		
		return Color.white;
	}
	
	public field getField(){
		return this;
	}
	
	public void setIntencity(int x)
	{
		if (intencity < x)
			intencity = x;
	}
	public void clearIntencity(int x)
	{
		intencity = 0;
	}
	
}
