import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class field {
	private ArrayList<object> ObjList = new ArrayList<object>();
	private ArrayList<object> NextIterationObjList = new ArrayList<object>();
	private int intencity;
	int col;
	int row;
	private Grid grid; /*grid this field is part of*/
	private boolean hasFire;
	private boolean hasDroid;
	private boolean hasWall;
	
	public field(int Row,int Col, Grid thisGrid){
		intencity = 0;
		hasFire = false;
		hasDroid = false;
		hasWall = false;
		col = Col;
		row = Row;
		grid = thisGrid;
	}
	
	public void AddObject(object x)	{
		NextIterationObjList.add(x);
	}
	
	public void RemoveObjext(object x)	{
		ObjList.remove(x);
	}
	
	public void UpdateField(Grid grid)	{
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
				
			x.runAI(grid,row,col);
		}
	}
	
	public Color GetColor(){
		if(hasDroid)
			return Constants.droidColor;
		
		if(hasFire)
			return Constants.fireColor;
		
		if(hasWall)
			return Constants.wallColor;
		
		if(intencity > 0)
			return Color.yellow;
		
		return Constants.floorColor;
	}
	
	public field getField(){
		return this;
	}
	
	public field getAdjecentField(Dir dir)
	{	
		field AdjField;
		
		switch(dir){
		case UP:
			AdjField = grid.getField(row, col +1);
			break;
		case DOWN:
			AdjField = grid.getField(row, col -1);
			break;
		case LEFT:
			AdjField = grid.getField(row -1, col);
			break;
		case RIGHT:
			AdjField = grid.getField(row +1, col);
			break;
		default:
			AdjField = grid.getField(row , col);
		}
		return AdjField;
		
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
	
	public boolean getPasseble()
	{
		if(hasFire || hasWall)
			return false;
		else
			return true;
	}
	
	public void finalizeFieldUpdate()
	{
		ObjList = NextIterationObjList;
		NextIterationObjList = new ArrayList<object>();
	}
	
}
