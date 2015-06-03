package neuralfire.core;

import java.util.Random;

public class Droid extends WorldObject{
	public static ThreadLocal<Integer> droidNo;
	
	public Droid(){
		if(droidNo == null){
			droidNo = new ThreadLocal<Integer>();
			droidNo.set(0);
		}
		
		movable = true;
		stackable = true;
		droidNo.set(droidNo.get() + 1);
	}
	
	public void relay(Grid grid,int row, int col){
		
		if(doFireWalk(grid, row, col)){
			this.spread(grid, row, col, Constants.yellRadius, Constants.yellVolume);
		}
		else if (followYelling(grid, row, col)){
			this.spread(grid, row, col, Constants.yellRadius, grid.getField(row, col).getVolume());
		} 
	}
	
	public void runAI(Grid grid,int row, int col)
	{
		IWalkAlgorithm walk;
		
		// TODO include this once firewalk is implemented
		if(doFireWalk(grid, row, col)){
			this.spread(grid, row, col, Constants.yellRadius,Constants.yellVolume);
			walk = new FireWalk();
		}
		else if (followYelling(grid, row, col)){
			//this.spread(grid, row, col, Constants.yellRadius, grid.getField(row, col).getVolume());
			walk = new YellWalk();
		}
		else
			walk = new ExplorationWalk();
		
		move(walk.performWalk(grid, row, col), grid, row, col);
		//move(Constants.Dir.LEFT, grid, row, col);
	}
	
	
	private boolean doFireWalk(Grid grid,int row, int col){
		// check if we perceive any fire
		return grid.getField(row, col).getPerceivedFireIntensity() > 0;
	}
	
	private boolean followYelling(Grid grid,int row, int col){
		// check if we perceive any fire
		return grid.getField(row, col).getPerceivedVolume() > 0;
	}
	
	
	private void move(Constants.Dir dir, Grid grid,int row, int col)
	{
		grid.getField(row, col).traversePath(dir, this);
	}
	

}