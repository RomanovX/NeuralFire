package neuralfire.core;

import java.awt.Color;


public class Constants {
	
	public static Color droidColor = Color.green;
	public static Color fireColor = Color.red;
	public static Color wallColor = Color.black;
	public static Color floorColor = Color.white;
	public static Color lineColor = Color.black;
	public static Color backgroundColor = Color.white;
	
	public static int preferredSquareSize = 10;
	public static int pheromoneIncrease = 5;
	
	public static int scaleUI = 5;
	
	public static int sleepDuration = 200;
	
	public static enum Dir {
		UP, DOWN, LEFT, RIGHT, STAY 
	}
	public static int fireRadius = 3;
	public static int yellRadius = 5;
	public static int fireIntensity = fireRadius + yellRadius;;
}
