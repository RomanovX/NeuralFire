package neuralfire.core;

import java.awt.Color;
import java.util.ArrayList;


public class Field {
	private ArrayList<WorldObject> ObjList = new ArrayList<WorldObject>();
	private ArrayList<WorldObject> NextIterationObjList = new ArrayList<WorldObject>();
	private int intensity;
	int col;
	int row;
	private Grid grid; /*grid this field is part of*/
	private boolean hasFire;
	private boolean hasDroid;
	private boolean hasWall;
	
	public Field(int Row,int Col, Grid thisGrid){
		intensity = 0;
		hasFire = false;
		hasDroid = false;
		hasWall = false;
		col = Col;
		row = Row;
		grid = thisGrid;
	}
	
	public void AddObject(WorldObject x)	{
		NextIterationObjList.add(x);
	}
	
	public void RemoveObjext(WorldObject x)	{
		ObjList.remove(x);
	}
	
	public void UpdateField(Grid grid)	{
		hasFire = false;
		hasDroid = false;
		hasWall = false;
		
		for(WorldObject x: ObjList){
			if(x instanceof Fire)
				hasFire = true;
			
			if(x instanceof Droid)
				hasDroid = true;
			
			if(x instanceof Wall)
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
		
		if(intensity > 0)
			return Color.yellow;
		
		return Constants.floorColor;
	}
	
	public Field getField(){
		return this;
	}
	
	public Field getAdjecentField(Constants.Dir dir)
	{	
		Field AdjField;
		
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
		if (intensity < x)
			intensity = x;
	}
	public void clearIntencity(int x)
	{
		intensity = 0;
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
		NextIterationObjList = new ArrayList<WorldObject>();
	}
	
}
