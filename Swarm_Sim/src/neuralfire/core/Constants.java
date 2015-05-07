package neuralfire.core;

import java.awt.Color;


public class Constants {
	public static int configuration = 3;
	
	public static Color droidColor = Color.green;
	public static Color fireColor = Color.red;
	public static Color wallColor = Color.black;
	public static Color floorColor = Color.white;
	public static Color lineColor = Color.black;
	public static Color backgroundColor = Color.white;
	
	public static boolean debug = false;
	
	public static int preferredSquareSize = 10;
	public static int pheromoneIncrease = 50;
	
	public static int scaleUI = 1;
	
	public static int sleepDuration = 20;
	
	public static String mapFile = "map2";
	
	public static enum Dir {
		UP, DOWN, LEFT, RIGHT, STAY 
	}
	
	public static double volumeDecay = 2;
	public static double volumeDecayThreshold = 1;
	public static int fireRadius = 10;
	public static int yellRadius = 20;
	public static int relays = 2;
	public static int fireIntensity = fireRadius + relays*yellRadius;
}
