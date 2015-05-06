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
	private Path upPath;
	private Path downPath;
	private Path leftPath;
	private Path rightPath;
	
	public Field(int Row,int Col, Grid thisGrid){
		intensity = 0;
		hasFire = false;
		hasDroid = false;
		hasWall = false;
		col = Col;
		row = Row;
		grid = thisGrid;
		upPath = null;
		downPath = null;
		leftPath = null;
		rightPath = null;
	}
	
	public void AddPath(Path path, Constants.Dir dir){
		switch(dir){
		case UP:
			upPath = path;
			break;
		case DOWN:
			downPath = path;
			break;
		case LEFT:
			leftPath = path;
			break;
		case RIGHT:
			rightPath = path;
			break;
		}
	}
	
	public void traversePath(Constants.Dir dir, WorldObject traversingObject){
		switch(dir){
		case UP:
			if(upPath != null && upPath.getOtherField(this).getPasseble()){
				upPath.getOtherField(this).AddObject(traversingObject);
				upPath.incresePheromoneIntensity(Constants.pheromoneIncrease);
			} else{
				this.AddObject(traversingObject);
			}
			break;
		case DOWN:
			if(downPath != null && downPath.getOtherField(this).getPasseble()){
				downPath.getOtherField(this).AddObject(traversingObject);
				downPath.incresePheromoneIntensity(Constants.pheromoneIncrease);
			} else{
				this.AddObject(traversingObject);
			}
			break;
		case LEFT:
			if(leftPath != null && leftPath.getOtherField(this).getPasseble()){
				leftPath.getOtherField(this).AddObject(traversingObject);
				leftPath.incresePheromoneIntensity(Constants.pheromoneIncrease);
			} else{
				this.AddObject(traversingObject);
			}
			break;
		case RIGHT:
			if(rightPath != null && rightPath.getOtherField(this).getPasseble()){
				rightPath.getOtherField(this).AddObject(traversingObject);
				rightPath.incresePheromoneIntensity(Constants.pheromoneIncrease);
			} else{
				this.AddObject(traversingObject);
			}
			break;
		}
	}
	
	public int getConcentratedPheromoneCount(){
		int total = 0;
		if(downPath != null)
			total = total + downPath.getPheromoneIntensity();
		if(upPath != null)
			total = total + upPath.getPheromoneIntensity();
		if(leftPath != null)
			total = total + leftPath.getPheromoneIntensity();
		if(rightPath != null)
			total = total + rightPath.getPheromoneIntensity();
		return total;
	}
	
	public Path getPath(Constants.Dir dir){
		switch(dir){
		case UP:
			return upPath;
		case DOWN:
			return downPath;
		case LEFT:
			return leftPath;
		case RIGHT:
			return rightPath;
		}
		return null;
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
			AdjField = grid.getField(row-1, col);
			break;
		case DOWN:
			AdjField = grid.getField(row+1, col);
			break;
		case LEFT:
			AdjField = grid.getField(row, col-1);
			break;
		case RIGHT:
			AdjField = grid.getField(row, col+1);
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

	public Path getDownPath() {
		return downPath;
	}

	public Path getUpPath() {
		return upPath;
	}

	public Path getLeftPath() {
		return leftPath;
	}

	public Path getRightPath() {
		return rightPath;
	}


}
